package gr.aueb.cf.medicalcare.repository;

import gr.aueb.cf.medicalcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    // Find a doctor by user username.
    Optional<Doctor> findDoctorByUserUsername(String username);
    // Find all doctors by personal details lastname.
    List<Doctor> findDoctorsByPersonalDetailsLastname(String lastname);
    // Find a doctor by personal details firstname.
    Optional<Doctor> findDoctorByPersonalDetailsSsid(String ssid);
    // Find all doctors with a specific specialization.
    List<Doctor> findDoctorsBySpecializationSpecializationName(String specializationName);
    // Count all doctors with a specific specialization.
    Long countDoctorBySpecializationSpecializationName(String specializationName);

}
