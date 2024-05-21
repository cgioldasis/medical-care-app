package gr.aueb.cf.medicalcare.service;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorRegisterDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorUpdateDTO;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.service.exception.DoctorNotFoundException;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    // Find a doctor by username.
    Doctor getDoctorByUserUsername(String username) throws UserNotFoundException;
    // Find a doctor by lastname
    List<Doctor> getDoctorsByLastname(String lastname) throws  DoctorNotFoundException;
    // Find a doctor by ssid
    Doctor getDoctorBySsid(String ssid) throws DoctorNotFoundException;
    // Find a doctor by specialization
    List<Doctor> getDoctorsBySpecialization(String specializationName) throws DoctorNotFoundException;
    // Register a new doctor
    Doctor registerNewDoctor(DoctorRegisterDTO dto) throws EntityAlreadyExistsException;
    // Update a doctor
    Doctor updateDoctor(DoctorUpdateDTO dto) throws DoctorNotFoundException;
    // Delete a doctor
    Doctor deleteDoctor(Long id);
    // Count all doctors with a specific specialization
    Long countDoctorsBySpecialization(String specializationName);


}
