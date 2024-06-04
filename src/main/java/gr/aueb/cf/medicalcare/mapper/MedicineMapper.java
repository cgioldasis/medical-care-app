package gr.aueb.cf.medicalcare.mapper;

import gr.aueb.cf.medicalcare.dto.medicine.MedicineInsertDTO;
import gr.aueb.cf.medicalcare.dto.medicine.MedicineReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.medicine.MedicineUpdateDTO;
import gr.aueb.cf.medicalcare.model.Medicine;
import org.hibernate.sql.Update;

public class MedicineMapper {

    private MedicineMapper() {
    }

    public static Medicine mapMedicineDTOToMedicine(MedicineInsertDTO dto) {
        Medicine medicine = new Medicine();
        medicine.setMedicineName(dto.getMedicineName());
        medicine.setActiveSubstance(dto.getActiveSubstance());
        medicine.setManufacturer(dto.getManufacturer());
        medicine.setManufactureDate(dto.getManufactureDate());
        medicine.setIsActive(true);
        return medicine;
    }

    public static Medicine mapMedicineDTOToMedicine(MedicineUpdateDTO dto) {
        Medicine medicine = new Medicine();
        medicine.setId(dto.getId());
        medicine.setMedicineName(dto.getMedicineName());
        medicine.setActiveSubstance(dto.getActiveSubstance());
        medicine.setManufacturer(dto.getManufacturer());
        medicine.setManufactureDate(dto.getManufactureDate());
        medicine.setExpirationDate(dto.getExpirationDate());
        medicine.setIsActive(true);
        return medicine;
    }

    public static MedicineReadOnlyDTO mapMedicineToMedicineReadOnlyDTO(Medicine medicine) {
        MedicineReadOnlyDTO dto = new MedicineReadOnlyDTO();
        dto.setId(medicine.getId());
        dto.setMedicineName(medicine.getMedicineName());
        dto.setActiveSubstance(medicine.getActiveSubstance());
        dto.setManufacturer(medicine.getManufacturer());
        dto.setManufactureDate(medicine.getManufactureDate());
        dto.setExpirationDate(medicine.getExpirationDate());
        return dto;
    }
}
