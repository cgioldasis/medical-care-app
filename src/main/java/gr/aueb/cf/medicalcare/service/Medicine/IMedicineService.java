package gr.aueb.cf.medicalcare.service.Medicine;

import gr.aueb.cf.medicalcare.dto.medicine.MedicineInsertDTO;
import gr.aueb.cf.medicalcare.dto.medicine.MedicineUpdateDTO;
import gr.aueb.cf.medicalcare.model.Medicine;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IMedicineService {
    // Find medicine by medicine name
    Medicine findMedicineByMedicineName(String name) throws EntityNotFoundException;
    // Find all medicines
    List<Medicine> findAllMedicines();
    // Save a new medicine
    Medicine addMedicine(MedicineInsertDTO dto) throws EntityAlreadyExistsException;
    // Update an existing medicine
    Medicine updateMedicine(MedicineUpdateDTO dto);
    // Delete a medicine
    void deleteMedicine(Long id);
}
