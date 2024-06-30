package gr.aueb.cf.medicalcare.service.Patient;

import gr.aueb.cf.medicalcare.dto.patient.PatientRegisterDTO;
import gr.aueb.cf.medicalcare.dto.patient.PatientUpdateDTO;
import gr.aueb.cf.medicalcare.model.Patient;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Set;

public interface IPatientService {

    List<Patient> getPatients() throws EntityNotFoundException;
    List<Patient> getPatientsByDoctorUsername(String username) throws EntityNotFoundException;
    Patient registerPatient(PatientRegisterDTO dto, String doctorsUsername) throws EntityAlreadyExistsException;
    Patient updatePatient(PatientUpdateDTO dto) throws EntityNotFoundException;
    void deletePatient(Long id) throws EntityNotFoundException;
}
