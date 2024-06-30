package gr.aueb.cf.medicalcare.mapper;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorForAdminListDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorRegisterDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorUpdateDTO;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.model.PersonalDetails;
import gr.aueb.cf.medicalcare.model.Specialization;
import gr.aueb.cf.medicalcare.model.User;

import java.util.Date;

/**
 * The DoctorMapper class.
 */
public class DoctorMapper {

    /**
     * Private constructor to hide the implicit public one.
     */
    private DoctorMapper() {}

    /**
     * Extracts a doctor from a DoctorRegisterDTO.
     * @param dto The DoctorRegisterDTO
     * @return The Doctor
     */
    public static PersonalDetails extractPersonalDetailsFromDoctorRegisterDTO(DoctorRegisterDTO dto) {
        PersonalDetails personalDetails = new PersonalDetails(dto.getSsid(), dto.getFirstname(), dto.getLastname(),
                dto.getGender(), new Date(), dto.getPhone());
        personalDetails.setIsActive(true);
        return personalDetails;
    }

/**
     * Extracts a doctor from a DoctorUpdateDTO.
     * @param dto The DoctorUpdateDTO
     * @return The Doctor
     */
    public static PersonalDetails extractPersonalDetailsFromDoctorUpdateDTO(DoctorUpdateDTO dto) {
        PersonalDetails personalDetails = new PersonalDetails(dto.getSsid(), dto.getFirstname(), dto.getLastname(),
                dto.getGender(), new Date(), dto.getPhone());
        personalDetails.setIsActive(true);
        return personalDetails;
    }


    /**
     * Extract a user from a DoctorRegisterDTO.
     * @param dto   The DoctorRegisterDTO
     * @return      The User
     */
    public static User extractUserFromDoctorRegisterDTO(DoctorRegisterDTO dto) {
        User user = User.getNewUserWithDoctorRole(dto.getUsername(), dto.getPassword(), dto.getEmail());
        user.setIsActive(true);
        return user;
    }

/**
     * Extract a user from a DoctorUpdateDTO.
     * @param dto   The DoctorUpdateDTO
     * @return      The User
     */
    public static User extractUserFromDoctorUpdateDTO(DoctorUpdateDTO dto) {
        User user = User.getNewUserWithDoctorRole(dto.getUsername(), dto.getPassword(), dto.getEmail());
        user.setIsActive(true);
        return user;
    }

    /**
     * Extract a specialization from a DoctorRegisterDTO.
     * @param dto   The DoctorRegisterDTO
     * @return      The Specialization
     */
    public static Specialization extractSpecializationFromDoctorRegisterDTO(DoctorRegisterDTO dto) {
        return new Specialization(dto.getSpecializationName(), dto.getDescription(), true);
    }

/**
     * Extract a specialization from a DoctorUpdateDTO.
     * @param dto   The DoctorUpdateDTO
     * @return      The Specialization
     */
    public static Specialization extractSpecializationFromDoctorUpdateDTO(DoctorUpdateDTO dto) {
        return new Specialization(dto.getSpecializationName(), dto.getDescription(), true);
    }

    /**
     * Extract a DoctorReadOnlyDTO from a Doctor.
     * @param doctor The Doctor
     * @return The DoctorReadOnlyDTO
     */
    public static DoctorReadOnlyDTO extractDoctorReadOnlyDTOFromDoctor(Doctor doctor) {
        return new DoctorReadOnlyDTO(doctor.getId(), doctor.getUser().getUsername(),
                doctor.getUser().getEmail(), doctor.getPersonalDetails().getFirstname(),
                doctor.getPersonalDetails().getLastname(), doctor.getPersonalDetails().getSsid(),
                doctor.getSpecialization().getSpecializationName(), doctor.getSpecialization().getDescription(),
                doctor.getPersonalDetails().getGender(), doctor.getPersonalDetails().getPhone());
    }

    public static DoctorForAdminListDTO extractDoctorForAdminListDTOFromDoctor(Doctor doctor) {
        return new DoctorForAdminListDTO(doctor.getId(), doctor.getUser().getUsername(),
                doctor.getUser().getEmail(),doctor.getPersonalDetails().getFirstname(),doctor.getPersonalDetails()
                .getLastname(),doctor.getPersonalDetails().getSsid(),doctor.getSpecialization().getSpecializationName(),
                doctor.getPersonalDetails().getPhone());
    }
}
