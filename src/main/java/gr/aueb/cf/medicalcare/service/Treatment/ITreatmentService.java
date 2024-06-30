package gr.aueb.cf.medicalcare.service.Treatment;

import gr.aueb.cf.medicalcare.dto.treatment.TreatmentRegisterDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentUpdateDTO;
import gr.aueb.cf.medicalcare.model.Treatment;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ITreatmentService {
    // Getting a treatment by its name.
    Treatment getTreatmentByName(String treatmentName);
    // Getting a treatment by its ID.
    Treatment getTreatmentById(Long id);
    // Getting a treatment by the name of a medicine used in it.
    List<Treatment> getTreatmentsByMedicineName(String medicineName);
    // Getting a treatment by the SSID of the doctor prescribing it.
    List<Treatment> getTreatmentsByDoctorPersonalDetailsLastname(String ssid);
    // Getting a treatment by the SSID of the patient receiving it.
    Treatment getTreatmentByPatientSSID(String ssid);
    // Getting all the treatments.
    List<Treatment> getAllTreatments();
    // Inserting a new treatment.
    Treatment insertTreatment(TreatmentRegisterDTO dto, String doctorUsername) throws EntityNotFoundException;
    // Updating a treatment.
    Treatment updateTreatment(TreatmentUpdateDTO dto) throws EntityNotFoundException;
    // Deleting a treatment.
    Treatment deleteTreatment(Long id);

}
