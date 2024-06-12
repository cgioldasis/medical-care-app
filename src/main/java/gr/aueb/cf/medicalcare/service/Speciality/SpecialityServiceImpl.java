package gr.aueb.cf.medicalcare.service.Speciality;

import gr.aueb.cf.medicalcare.model.Specialization;
import gr.aueb.cf.medicalcare.repository.SpecializationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class SpecialityServiceImpl implements ISpecialityService {

    private  SpecializationRepository specializationRepository;
    @Override
    public List<Specialization> getAllSpecialities() throws EntityNotFoundException {
        List<Specialization> specialities;

        try {
            specialities = specializationRepository.findAll();
            if (specialities.isEmpty()) {
                throw new EntityNotFoundException("No specialities found");
            }
            log.info("The specialities found successfully");
            return specialities;
        } catch (EntityNotFoundException e) {
            log.error("No specialties found");
            throw e;
        }
    }
}
