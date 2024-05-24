package gr.aueb.cf.medicalcare.service.Treatmen;

import gr.aueb.cf.medicalcare.dto.treatment.TreatmentRegisterDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentUpdateDTO;
import gr.aueb.cf.medicalcare.model.Treatment;

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
    // Getting all the treatments.
    List<Treatment> getAllTreatments();
    // Inserting a new treatment.
    Treatment insertTreatment(TreatmentRegisterDTO dto);
    // Updating a treatment.
    Treatment updateTreatment(TreatmentUpdateDTO dto);
    // Deleting a treatment.
    Treatment deleteTreatment(Long id);

}
