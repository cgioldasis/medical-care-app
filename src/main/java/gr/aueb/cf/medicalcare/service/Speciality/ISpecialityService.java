package gr.aueb.cf.medicalcare.service.Speciality;

import gr.aueb.cf.medicalcare.model.Specialization;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ISpecialityService {
    List<Specialization> getAllSpecialities() throws EntityNotFoundException;
}
