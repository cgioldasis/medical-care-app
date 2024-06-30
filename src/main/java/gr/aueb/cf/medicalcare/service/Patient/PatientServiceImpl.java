package gr.aueb.cf.medicalcare.service.Patient;

import gr.aueb.cf.medicalcare.dto.patient.PatientRegisterDTO;
import gr.aueb.cf.medicalcare.dto.patient.PatientUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.PatientMapper;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.model.Patient;
import gr.aueb.cf.medicalcare.model.PersonalDetails;
import gr.aueb.cf.medicalcare.repository.DoctorRepository;
import gr.aueb.cf.medicalcare.repository.PatientRepository;
import gr.aueb.cf.medicalcare.security.JwtUtil;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PatientServiceImpl implements IPatientService{

    //  DI
    private final PatientRepository patientRepository;
    private final JwtUtil jwtUtil;
    private final DoctorRepository doctorRepository;

    /**
     * Fetches all patients
     * @return List of patients
     * @throws EntityNotFoundException
     */
    @Override
    public List<Patient> getPatients() throws EntityNotFoundException {
        List<Patient> patients = new ArrayList<>();
        try {
            patients = patientRepository.findAll();
            if (patients.isEmpty()) {
                throw new EntityNotFoundException("No patients found");
            }
            log.info("Patients fetched successfully");
            return patients;
        } catch (Exception e) {
            log.error("Error while fetching patients", e);
            throw e;
        }
    }

    @Override
    public List<Patient> getPatientsByDoctorUsername(String username) throws EntityNotFoundException {
        Patient patient;
        try {
            Doctor doctor = doctorRepository.findDoctorByUserUsername(username).orElseThrow(
                    () -> new EntityNotFoundException("Doctor not found"));
            List<Patient> patients = patientRepository.
                    findPatientByDoctorUserUsername(username);
            if (patients.isEmpty()) {
                throw new EntityNotFoundException("No patients found");
            }
            log.info("Patients fetched successfully with doctor: "
                    + doctor.getPersonalDetails().getLastname() + " username: " + username);
            return patients;
        } catch (Exception e) {
            log.error("Error while fetching patients", e);
            throw e;
        }
    }

    /**
     * Registers a patient
     * @param dto   PatientRegisterDTO
     * @param doctorsUsername  doctorsUsername
     * @return      Patient
     * @throws EntityAlreadyExistsException     If patient already exists
     */
    @Transactional
    @Override
    public Patient registerPatient(PatientRegisterDTO dto, String doctorsUsername) throws EntityAlreadyExistsException {
        Patient patient;
        PersonalDetails personalDetails;
        Doctor doctor;
        try {
            patient = PatientMapper.mapPatientRegisterDTOToPatient(dto);
            personalDetails = PatientMapper.mapPatientRegisterDTOToPersonalDetails(dto);
            if (patientRepository.findPatientByPersonalDetailsSsid(personalDetails.getSsid()).isPresent()) {
                throw new EntityAlreadyExistsException(PatientServiceImpl.class,
                        "Patient with SSID: " + personalDetails.getSsid() + " already exists");
            }
            doctor = doctorRepository.findDoctorByUserUsername(doctorsUsername).orElseThrow(
                    () -> new EntityNotFoundException("Doctor not found"));
            patient.addPersonalDetails(personalDetails);
            patient.addDoctor(doctor);
            patientRepository.save(patient);
            log.info("Patient registered successfully");
            return patient;
        } catch (EntityAlreadyExistsException e) {
            log.error("Error while registering patient", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Patient updatePatient(PatientUpdateDTO dto) throws EntityNotFoundException {
        Patient patient;
        Patient updatedPatient;
        PersonalDetails personalDetails;
        Doctor doctor;
        try {

            patient = patientRepository.findPatientByPersonalDetailsSsid(dto.getSsid()).orElseThrow(
                    () -> new EntityAlreadyExistsException(PatientServiceImpl.class,
                            "Patient with SSID: " + dto.getSsid() + " already exists"));
            updatedPatient = PatientMapper.mapPatientUpdateDTOToPatient(dto);
            personalDetails = PatientMapper.mapPatientUpdateDTOToPersonalDetails(dto);
            if (patientRepository.findPatientByPersonalDetailsSsid(personalDetails.getSsid()).isPresent()) {
                throw new EntityAlreadyExistsException(PatientServiceImpl.class,
                        "Patient with SSID: " + personalDetails.getSsid() + " already exists");
            }
            // personal details
            personalDetails.setIsActive(true);
            personalDetails.setId(patient.getPersonalDetails().getId());
            updatedPatient.addPersonalDetails(personalDetails);

            // doctor
            doctor = patient.getDoctor();
            updatedPatient.addDoctor(doctor);

            patientRepository.save(updatedPatient);
            log.info("Patient updated successfully");
            return updatedPatient;

        } catch (EntityAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePatient(Long id) throws EntityNotFoundException {
        try {
            patientRepository.deleteById(id);
            log.info("Patient deleted successfully");
        } catch (Exception e) {
            log.error("Error while deleting patient", e);
            throw e;
        }
    }
}
