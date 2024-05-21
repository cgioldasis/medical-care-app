package gr.aueb.cf.medicalcare.service;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorRegisterDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.DoctorMapper;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.model.PersonalDetails;
import gr.aueb.cf.medicalcare.model.Specialization;
import gr.aueb.cf.medicalcare.model.User;
import gr.aueb.cf.medicalcare.repository.DoctorRepository;
import gr.aueb.cf.medicalcare.repository.SpecializationRepository;
import gr.aueb.cf.medicalcare.security.SecUtil;
import gr.aueb.cf.medicalcare.service.exception.DoctorNotFoundException;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    // Injecting the DoctorService
    private final DoctorRepository doctorRepository;

    private final SpecializationRepository specializationRepository;

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

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specializationName) throws DoctorNotFoundException {
        List<Doctor> doctors = new ArrayList<>();
        try {
            doctors = doctorRepository.findDoctorsBySpecializationSpecializationName(specializationName);
            if (doctors.isEmpty()) {
                throw new DoctorNotFoundException("Doctor with specialization: " + specializationName + " not found");
            }
            return doctors;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
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
            user.setPassword(SecUtil.hashPassword(user.getPassword()));
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

            doctorRepository.save(doctor);
            log.info("Doctor added");
            return doctor;
        } catch (EntityAlreadyExistsException e) {
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
    public Doctor updateDoctor(DoctorUpdateDTO dto) throws DoctorNotFoundException {
        Doctor doctor;
        User user;
        Specialization specialization;
        PersonalDetails personalDetails;
        try {
            doctor = doctorRepository.findById(dto.getId()).orElseThrow(() ->
                    new DoctorNotFoundException("Doctor with id: " + dto.getId() + " not found"));
            user = doctor.getUser();
            specialization = doctor.getSpecialization();
            personalDetails = doctor.getPersonalDetails();

            doctor.addUser(DoctorMapper.extractUserFromDoctorUpdateDTO(dto, user));
            doctor.addPersonalDetails(DoctorMapper.extractPersonalDetailsFromDoctorUpdateDTO(dto, personalDetails));
            specialization = DoctorMapper.extractSpecializationFromDoctorUpdateDTO(dto, specialization);
            if (specializationRepository.findSpecializationBySpecializationName(
                    specialization.getSpecializationName()).isPresent()) {
                specialization = specializationRepository.findSpecializationBySpecializationName(
                        specialization.getSpecializationName()).get();
            }

            doctor.addSpecialization(specialization);
            doctorRepository.save(doctor);
            log.info("Doctor updated");
            return doctor;

        } catch (DoctorNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }


    }

    @Override
    public Doctor deleteDoctor(Long id) {
        return null;
    }

    @Override
    public Long countDoctorsBySpecialization(String specializationName) {
        return 0L;
    }
}
