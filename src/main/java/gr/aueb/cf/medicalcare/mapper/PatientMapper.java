package gr.aueb.cf.medicalcare.mapper;

import gr.aueb.cf.medicalcare.dto.patient.PatientReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.patient.PatientRegisterDTO;
import gr.aueb.cf.medicalcare.dto.patient.PatientUpdateDTO;
import gr.aueb.cf.medicalcare.model.Patient;
import gr.aueb.cf.medicalcare.model.PersonalDetails;

public class PatientMapper {
    private PatientMapper() {
    }

    /**
     * Maps a PatientRegisterDTO object to a Patient object.
     *
     * @param patientRegisterDTO The PatientRegisterDTO object to map.
     * @return The mapped Patient object.
     */
    public static Patient mapPatientRegisterDTOToPatient(PatientRegisterDTO patientRegisterDTO) {
        Patient patient = new Patient();
        patient.setDiseaseDescription(patientRegisterDTO.getDiseaseDescription());
        return patient;
    }

    /**
     * Maps a PatientRegisterDTO object to a PersonalDetails object.
     * @param patientRegisterDTO The PatientRegisterDTO object to map.
     * @return The mapped PersonalDetails object.
     */
    public static PersonalDetails mapPatientRegisterDTOToPersonalDetails(PatientRegisterDTO patientRegisterDTO) {
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setSsid(patientRegisterDTO.getSsid());
        personalDetails.setFirstname(patientRegisterDTO.getFirstname());
        personalDetails.setLastname(patientRegisterDTO.getLastname());
        personalDetails.setGender(patientRegisterDTO.getGender());
        personalDetails.setBirthdate(patientRegisterDTO.getBirthdate());
        personalDetails.setPhone(patientRegisterDTO.getPhone());
        personalDetails.setIsActive(true);
        return personalDetails;
    }


    public static Patient mapPatientUpdateDTOToPatient(PatientUpdateDTO patientUpdateDTO) {
        Patient patient = new Patient();
        patient.setId(patientUpdateDTO.getId());
        PersonalDetails personalDetails = new PersonalDetails(
                patientUpdateDTO.getSsid(),
                patientUpdateDTO.getFirstname(),
                patientUpdateDTO.getLastname(),
                patientUpdateDTO.getGender(),
                patientUpdateDTO.getBirthdate(),
                patientUpdateDTO.getPhone()
        );
        patient.setPersonalDetails(personalDetails);
        patient.setDiseaseDescription(patientUpdateDTO.getDiseaseDescription());
        return patient;
    }

    public static PersonalDetails mapPatientUpdateDTOToPersonalDetails(PatientUpdateDTO patientUpdateDTO) {
        return new PersonalDetails(
                patientUpdateDTO.getSsid(),
                patientUpdateDTO.getFirstname(),
                patientUpdateDTO.getLastname(),
                patientUpdateDTO.getGender(),
                patientUpdateDTO.getBirthdate(),
                patientUpdateDTO.getPhone()
        );
    }

    public static PatientReadOnlyDTO mapPatientToPatientReadOnlyDTO(Patient patient) {
        PatientReadOnlyDTO patientReadOnlyDTO = new PatientReadOnlyDTO();
        patientReadOnlyDTO.setId(patient.getId());
        patientReadOnlyDTO.setSsid(patient.getPersonalDetails().getSsid());
        patientReadOnlyDTO.setFirstname(patient.getPersonalDetails().getFirstname());
        patientReadOnlyDTO.setLastname(patient.getPersonalDetails().getLastname());
        patientReadOnlyDTO.setGender(patient.getPersonalDetails().getGender());
        patientReadOnlyDTO.setBirthdate(patient.getPersonalDetails().getBirthdate());
        patientReadOnlyDTO.setPhone(patient.getPersonalDetails().getPhone());
        patientReadOnlyDTO.setDiseaseDescription(patient.getDiseaseDescription());
        return patientReadOnlyDTO;
    }
}
