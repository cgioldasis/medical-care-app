package gr.aueb.cf.medicalcare.mapper;

import gr.aueb.cf.medicalcare.dto.treatment.TreatmentReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentRegisterDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentUpdateDTO;
import gr.aueb.cf.medicalcare.model.Medicine;
import gr.aueb.cf.medicalcare.model.Patient;
import gr.aueb.cf.medicalcare.model.PersonalDetails;
import gr.aueb.cf.medicalcare.model.Treatment;

import java.util.Set;

public class TreatmentMapper {

    private TreatmentMapper() {
    }

    public static Treatment mapTreatmentRegisterDTOToTreatment(TreatmentRegisterDTO treatmentRegisterDTO) {
        Treatment treatment = new Treatment();
        treatment.setTreatmentName(treatmentRegisterDTO.getTreatmentName());
        treatment.setStartTreatment(treatmentRegisterDTO.getStartDate());
        treatment.setEndTreatment(treatmentRegisterDTO.getEndDate());
        treatment.setIsActive(true);
        return treatment;
    }

    public static PersonalDetails mapTreatmentRegisterDTOToPersonalDetails(TreatmentRegisterDTO treatmentRegisterDTO) {
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setSsid(treatmentRegisterDTO.getPatientSsid());
        return personalDetails;
    }


    public static Treatment mapTreatmentUpdateDTOToTreatment(TreatmentUpdateDTO treatmentUpdateDTO) {
        Treatment treatment = new Treatment();
        treatment.setId(treatmentUpdateDTO.getId());
        treatment.setTreatmentName(treatmentUpdateDTO.getTreatmentName());
        treatment.setStartTreatment(treatmentUpdateDTO.getStartDate());
        treatment.setEndTreatment(treatmentUpdateDTO.getEndDate());
        return treatment;
    }


    public static TreatmentReadOnlyDTO mapTreatmentToTreatmentReadOnlyDTO(Treatment treatment) {
        TreatmentReadOnlyDTO treatmentReadOnlyDTO = new TreatmentReadOnlyDTO();
        treatmentReadOnlyDTO.setTreatmentName(treatment.getTreatmentName());
        treatmentReadOnlyDTO.setDoctorSsid(treatment.getDoctor().getPersonalDetails().getSsid());
        treatmentReadOnlyDTO.setPatientSsid(treatment.getPatient().getPersonalDetails().getSsid());
        treatmentReadOnlyDTO.setStartDate(treatment.getStartTreatment());
        treatmentReadOnlyDTO.setEndDate(treatment.getEndTreatment());
        treatmentReadOnlyDTO.setMedicineNames(Set.of(treatment.getMedicines().stream()
                .map(Medicine::getMedicineName).toArray(String[]::new)));
        return treatmentReadOnlyDTO;
    }

}
