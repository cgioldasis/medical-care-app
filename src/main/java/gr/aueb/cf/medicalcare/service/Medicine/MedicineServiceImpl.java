package gr.aueb.cf.medicalcare.service.Medicine;

import gr.aueb.cf.medicalcare.dto.medicine.MedicineInsertDTO;
import gr.aueb.cf.medicalcare.dto.medicine.MedicineUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.MedicineMapper;
import gr.aueb.cf.medicalcare.model.Medicine;
import gr.aueb.cf.medicalcare.repository.MedicineRepository;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Medicine Service Implementation
 */

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class MedicineServiceImpl implements IMedicineService{

    // Medicine Repository injection
    private MedicineRepository medicineRepository;

    /**
     * Find medicine by medicine name
     * @param name                          Medicine name
     * @return                              Medicine
     * @throws EntityNotFoundException      Medicine not found
     */
    @Override
    public Medicine findMedicineByMedicineName(String name) throws EntityNotFoundException {
        return medicineRepository.findMedicineByMedicineName(name).orElseThrow(() ->
                new EntityNotFoundException("Medicine with name: " + name + " not found"));
    }

    /**
     * Find all medicines
     * @return                              List of medicines
     */
    @Override
    public List<Medicine> findAllMedicines()  {
        try {
            return medicineRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Save a new medicine
     * @param dto                           Medicine DTO
     * @return                              Medicine
     * @throws EntityAlreadyExistsException Medicine already exists
     */
    @Override
    @Transactional
    public Medicine addMedicine(MedicineInsertDTO dto) throws EntityAlreadyExistsException {
        Medicine medicine;
        try {
            if (medicineRepository.findMedicineByMedicineName(dto.getMedicineName()).isPresent()) {
                throw new EntityAlreadyExistsException(Medicine.class, dto.getMedicineName());
            }
            medicine = MedicineMapper.mapMedicineDTOToMedicine(dto);
            medicineRepository.save(medicine);
            log.info("Medicine with name: {} added successfully.", dto.getMedicineName());
            return medicine;
        } catch (EntityAlreadyExistsException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Update an existing medicine
     * @param dto                           Medicine DTO
     * @return                              Medicine
     */
    @Transactional
    @Override
    public Medicine updateMedicine(MedicineUpdateDTO dto) {
        Medicine medicine;
        try {
            medicine = MedicineMapper.mapMedicineDTOToMedicine(dto);
            medicineRepository.save(medicine);
            log.info("Medicine with name: {} updated successfully.", dto.getMedicineName());
            return medicine;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Delete a medicine
     * @param id                            Medicine id
     */
    @Override
    public void deleteMedicine(Long id) {
        try {
            medicineRepository.deleteById(id);
            log.info("Medicine with id: {} deleted successfully.", id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
