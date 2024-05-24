package gr.aueb.cf.medicalcare.repository;

import gr.aueb.cf.medicalcare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MedicineRepository extends JpaRepository<Medicine, Long>{
    // Find medicine by medicine name
    Optional<Medicine> findMedicineByMedicineName(String name);
    // Find all medicines with a specific activeSubstance
    List<Medicine> findMedicineByActiveSubstance(String activeSubstance);

}
