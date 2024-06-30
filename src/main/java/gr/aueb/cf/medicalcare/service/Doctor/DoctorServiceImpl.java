package gr.aueb.cf.medicalcare.service.Doctor;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorRegisterDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.DoctorMapper;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.model.PersonalDetails;
import gr.aueb.cf.medicalcare.model.Specialization;
import gr.aueb.cf.medicalcare.model.User;
import gr.aueb.cf.medicalcare.repository.DoctorRepository;
import gr.aueb.cf.medicalcare.repository.SpecializationRepository;
import gr.aueb.cf.medicalcare.service.exception.DoctorNotFoundException;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * The DoctorServiceImpl class.
 */

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    // Injecting the DoctorService
    private final DoctorRepository doctorRepository;

    private final SpecializationRepository specializationRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Get a doctor by username
     * @param username                  The username of the doctor
     * @return                          The doctor
     * @throws UserNotFoundException    If the doctor is not found
     */
    @Override
    public Doctor getDoctorByUserUsername(String username) throws UserNotFoundException {
        try {
            return doctorRepository.findDoctorByUserUsername(username).orElseThrow(() ->
                    new UserNotFoundException("Doctor with username: " + username + " not found"));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

/**
     * Get a doctor by lastname
     * @param lastname                        The id of the doctor
     * @return                          The doctor
 */
    @Override
    public List<Doctor> getDoctorsByLastname(String lastname) throws DoctorNotFoundException {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = doctorRepository.findDoctorsByPersonalDetailsLastname(lastname);
            if (doctors.isEmpty()) {
                throw new DoctorNotFoundException("Doctor with lastname: " + lastname + " not found");
            }
            log.info("The list of doctors with lastname: " + lastname + " found successfully");
            return doctors;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Get a doctor by ssid
     * @param ssid                        The ssid of the doctor
     * @return                          The doctor
     * @throws DoctorNotFoundException    If the doctor is not found
     */
    @Override
    public Doctor getDoctorBySsid(String ssid) throws DoctorNotFoundException {
        try {
            return doctorRepository.findDoctorByPersonalDetailsSsid(ssid).orElseThrow(() ->
                    new DoctorNotFoundException("Doctor with SSID: " + ssid + " not found"));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Get doctors by specialization
     * @param specializationName         The specialization name
     * @return                          The list of doctors
     * @throws DoctorNotFoundException    If no doctors are found
     */
    @Override
    public List<Doctor> getDoctorsBySpecialization(String specializationName) throws DoctorNotFoundException {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = doctorRepository.findDoctorsBySpecializationSpecializationName(specializationName);
            if (doctors.isEmpty()) {
                throw new DoctorNotFoundException("Doctor with specialization: " + specializationName + " not found");
            }
            log.info("The list of doctors with specialization: {} found successfully", specializationName);
            return doctors;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Get all doctors
     * @return                          The list of all doctors
     * @throws DoctorNotFoundException    If no doctors are found
     */
    @Override
    public List<Doctor> getAllDoctors() throws DoctorNotFoundException {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = doctorRepository.findAll();
            if (doctors.isEmpty()) {
                throw new DoctorNotFoundException("No doctors are registered in the system");
            }
            log.info("The list of all doctors found successfully");
            return doctors;
        } catch (DoctorNotFoundException e) {
            log.error("No doctors found");
            throw  e;
        }
    }

    /**
     * Register a new doctor
     * @param dto                       The doctor register DTO
     * @return                          The doctor
     * @throws EntityAlreadyExistsException If the doctor already exists
     */
    @Transactional
    @Override
    public Doctor registerNewDoctor(DoctorRegisterDTO dto) throws EntityAlreadyExistsException {
        Doctor doctor = new Doctor();
        User user;
        Specialization specialization;
        PersonalDetails personalDetails;


        try {
            user = DoctorMapper.extractUserFromDoctorRegisterDTO(dto);
            if (doctorRepository.findDoctorByUserUsername(user.getUsername()).isPresent()) {
                throw new EntityAlreadyExistsException(Doctor.class, user.getUsername());
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            specialization = DoctorMapper.extractSpecializationFromDoctorRegisterDTO(dto);
            if (specializationRepository.findSpecializationBySpecializationName(
                    specialization.getSpecializationName()).isPresent()) {
                specialization = specializationRepository.findSpecializationBySpecializationName(
                        specialization.getSpecializationName()).get();
            }
            personalDetails = DoctorMapper.extractPersonalDetailsFromDoctorRegisterDTO(dto);

            doctor.addSpecialization(specialization);
            doctor.addUser(user);
            doctor.addPersonalDetails(personalDetails);
            doctor.setIsActive(true);

            doctorRepository.save(doctor);
            log.info("Doctor added");
            return doctor;
        } catch (EntityAlreadyExistsException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Update a doctor
     * @param dto                       The doctor update DTO
     * @return                          The doctor
     * @throws DoctorNotFoundException    If the doctor is not found
     */
    @Override
    @Transactional
    public Doctor updateDoctor(DoctorUpdateDTO dto) throws DoctorNotFoundException {
        Doctor doctor;
        User updatedUser = new User();
        PersonalDetails updatedPersonalDetails = new PersonalDetails();
        Specialization updatedSpecialization = new Specialization();
        try {
            doctor = doctorRepository.findById(dto.getId()).orElseThrow(() ->
                    new DoctorNotFoundException("Doctor with id: " + dto.getId() + " not found"));

            updatedUser = DoctorMapper.extractUserFromDoctorUpdateDTO(dto);
            updatedPersonalDetails = DoctorMapper.extractPersonalDetailsFromDoctorUpdateDTO(dto);
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            updatedSpecialization = DoctorMapper.extractSpecializationFromDoctorUpdateDTO(dto);
            if (specializationRepository.findSpecializationBySpecializationName(
                    updatedSpecialization.getSpecializationName()).isPresent()) {
                updatedSpecialization = specializationRepository.findSpecializationBySpecializationName(
                        updatedSpecialization.getSpecializationName()).get();
            }

            updatedUser.setId(doctor.getUser().getId());
            updatedPersonalDetails.setId(doctor.getPersonalDetails().getId());

            doctor.addSpecialization(updatedSpecialization);
            doctor.addUser(updatedUser);
            doctor.addPersonalDetails(updatedPersonalDetails);
            doctorRepository.save(doctor);
            log.info("Doctor updated");
            return doctor;

        } catch (DoctorNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }


    }

    /**
     * Delete a doctor
     * @param id                        The id of the doctor
     * @return                          The doctor
     * @throws DoctorNotFoundException    If the doctor is not found
     */
    @Override
    public Doctor deleteDoctor(Long id) throws DoctorNotFoundException {
        Doctor doctor;
        try {
            doctor = doctorRepository.findById(id).orElseThrow(() ->
                    new DoctorNotFoundException("Doctor with id: " + id + " not found"));
            doctorRepository.deleteById(id);
            log.info("Doctor deleted");
            return doctor;
        } catch (DoctorNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Long countAllDoctors() {
        Long count;
        try {
            count = doctorRepository.count();
            log.info("The number of doctors is {}", count);
            return count;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Count all doctors with a specific specialization
     * @param specializationName         The specialization name
     * @return                          The number of doctors
     */
    @Override
    public Long countDoctorsBySpecialization(String specializationName) {
        Long count;
        try {
            count = doctorRepository.countDoctorBySpecializationSpecializationName(specializationName);
            log.info("The number of doctors with specialization: {} is {}", specializationName, count);
            return count;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
