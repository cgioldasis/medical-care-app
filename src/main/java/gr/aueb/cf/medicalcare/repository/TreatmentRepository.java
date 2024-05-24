package gr.aueb.cf.medicalcare.repository;

import gr.aueb.cf.medicalcare.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    // Find a treatment by treatment name.
    Optional<Treatment> findByTreatmentName(String treatmentName);
    // Find all treatments by a specific medicine name.
    @Query("SELECT t FROM Treatment t JOIN t.medicines m WHERE m.medicineName = :medicineName")
    List<Treatment> findTreatmentsByMedicineName(@Param("medicineName") String medicineName);

    // Find all treatments by a specific doctor's ssid.
    @Query("SELECT t From Treatment t JOIN t.doctors d where d.personalDetails = (" +
            "select p from PersonalDetails p where p.ssid = :ssid)")
    List<Treatment> findTreatmentsByDoctorPersonalDetailsLastname(@Param("ssid") String ssid);
}
