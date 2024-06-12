package gr.aueb.cf.medicalcare.repository;

import gr.aueb.cf.medicalcare.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
     Optional<Specialization> findSpecializationBySpecializationName(String name);
}
