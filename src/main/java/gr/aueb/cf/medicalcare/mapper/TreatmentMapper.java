package gr.aueb.cf.medicalcare.mapper;

import gr.aueb.cf.medicalcare.dto.treatment.TreatmentRegisterDTO;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.model.Medicine;
import gr.aueb.cf.medicalcare.model.PersonalDetails;
import gr.aueb.cf.medicalcare.model.Treatment;

import java.util.Set;

public class TreatmentMapper {

    private TreatmentMapper() {
    }

    public static Treatment extractTreatmentFromDTO(TreatmentRegisterDTO treatmentRegisterDTO) {
        return new Treatment(treatmentRegisterDTO.getTreatmentName(), treatmentRegisterDTO.getStartDate(),
                treatmentRegisterDTO.getEndDate());
    }

    public static Medicine extractMedicinesFromDTO(TreatmentRegisterDTO treatmentRegisterDTO) {
        return null;
    }
}
