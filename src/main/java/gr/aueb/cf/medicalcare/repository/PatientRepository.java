package gr.aueb.cf.medicalcare.repository;

import gr.aueb.cf.medicalcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findPatientByPersonalDetailsLastname(String lastname);
    Optional<Patient> findPatientByPersonalDetailsSsid(String ssid);
    List<Patient> findPatientByDoctorPersonalDetailsLastname(String lastname);
    List<Patient> findPatientByTreatmentTreatmentName(String treatmentName);
    List<Patient> findPatientByDoctorUserUsername(String username);
}
