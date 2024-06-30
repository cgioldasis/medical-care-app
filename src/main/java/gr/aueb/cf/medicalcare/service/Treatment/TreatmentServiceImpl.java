package gr.aueb.cf.medicalcare.service.Treatment;

import gr.aueb.cf.medicalcare.dto.treatment.TreatmentRegisterDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.TreatmentMapper;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.model.Medicine;
import gr.aueb.cf.medicalcare.model.Patient;
import gr.aueb.cf.medicalcare.model.Treatment;
import gr.aueb.cf.medicalcare.repository.DoctorRepository;
import gr.aueb.cf.medicalcare.repository.MedicineRepository;
import gr.aueb.cf.medicalcare.repository.PatientRepository;
import gr.aueb.cf.medicalcare.repository.TreatmentRepository;
import gr.aueb.cf.medicalcare.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TreatmentServiceImpl implements ITreatmentService{

    // Injecting the TreatmentRepository
    private final TreatmentRepository treatmentRepository;
    private final DoctorRepository doctorRepository;
    private final MedicineRepository medicineRepository;
    private final PatientRepository patientRepository;



    @Override
    public Treatment getTreatmentByName(String treatmentName) {
        Treatment treatment;
        try {
            // Return the treatment if found, otherwise throw an exception
            treatment = treatmentRepository.findByTreatmentName(treatmentName).orElseThrow(() ->
                    new EntityNotFoundException("Treatment with name: " + treatmentName + " not found"));
            log.info("Treatment with name: {} found", treatmentName);
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Treatment getTreatmentById(Long id) {
        Treatment treatment;
        try {
            // Return the treatment if found, otherwise throw an exception
            treatment = treatmentRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Treatment with id: " + id + " not found"));
            log.info("Treatment with id: {} found", id);
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Treatment> getTreatmentsByMedicineName(String medicineName) {
        List<Treatment> treatments = new ArrayList<>();
        try {
            treatments = treatmentRepository.findTreatmentsByMedicineName(medicineName);
            if (treatments.isEmpty()) {
                throw new EntityNotFoundException("Treatments with medicine " + medicineName + " not found");
            }
            log.info("Treatments with medicine {} found", medicineName);
            return treatments;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Treatment> getTreatmentsByDoctorPersonalDetailsLastname(String ssid) {
        List<Treatment> treatments = new ArrayList<>();
        try {
            treatments = treatmentRepository.findTreatmentsByDoctorPersonalDetailsLastname(ssid);
            if (treatments.isEmpty()) {
                throw new EntityNotFoundException("Treatments with doctor " + ssid + " not found");
            }
            log.info("Treatments with doctor {} found", ssid);
            return treatments;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Treatment getTreatmentByPatientSSID(String ssid) {
        Treatment treatment;
        try {
            treatment = treatmentRepository.findTreatmentByPatientPersonalDetailsSsid(ssid).orElseThrow(() ->
                    new EntityNotFoundException("Treatment with patient " + ssid + " not found"));
            log.info("Treatment with patient {} found", ssid);
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Treatment> getAllTreatments() {
        List<Treatment> treatments = new ArrayList<>();
        try {
            treatments = treatmentRepository.findAll();
            if (treatments.isEmpty()) {
                throw new EntityNotFoundException("No treatments found");
            }
            log.info("All treatments found");
            return treatments;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    @Transactional
    @Override
    public Treatment insertTreatment(TreatmentRegisterDTO dto, String doctorUsername) throws EntityNotFoundException {
        Treatment treatment;
        Doctor doctor;
        Medicine medicine;
        Patient patient;
        try {
            doctor = doctorRepository.findDoctorByUserUsername(doctorUsername).orElseThrow(() ->
                    new EntityNotFoundException("Doctor with SSID: " + doctorUsername + " not found"));
            treatment = TreatmentMapper.mapTreatmentRegisterDTOToTreatment(dto);
            patient = patientRepository.findPatientByPersonalDetailsSsid(dto.getPatientSsid()).orElseThrow(() ->
                    new EntityNotFoundException("Patient with SSID: " + dto.getPatientSsid() + " not found"));
            for (String medicineName : dto.getMedicineNames()) {
                medicine = medicineRepository.findMedicineByMedicineName(medicineName).orElseThrow(() ->
                        new EntityNotFoundException("Medicine with name: " + medicineName + " not found"));
                treatment.addMedicine(medicine);
            }

            treatment.addDoctor(doctor);
            treatment.addPatient(patient);

            treatmentRepository.save(treatment);
            log.info("Treatment with name: {} was added at {}", treatment.getTreatmentName(), LocalDateTime.now());
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Treatment updateTreatment(TreatmentUpdateDTO dto) throws EntityNotFoundException {
        Treatment treatment;
        Medicine medicine;
        Patient patient;
        try {
            treatment = TreatmentMapper.mapTreatmentUpdateDTOToTreatment(dto);
            for (String medicineName : dto.getMedicineNames()) {
                medicine = medicineRepository.findMedicineByMedicineName(medicineName).orElseThrow(() ->
                        new EntityNotFoundException("Medicine with name: " + medicineName + " not found"));
                treatment.addMedicine(medicine);
            }
            treatmentRepository.save(treatment);
            log.info("Treatment with name: {} was updated at {}", treatment.getTreatmentName(), LocalDateTime.now());
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;

        }
    }

    @Override
    public Treatment deleteTreatment(Long id) {
        Treatment treatment;
        try {
            treatment = treatmentRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Treatment with id: " + id + " not found"));
            treatmentRepository.delete(treatment);
            log.info("Treatment with id: {} was deleted at {}", id, LocalDateTime.now());
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}


