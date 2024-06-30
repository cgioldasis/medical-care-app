package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.patient.PatientReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.patient.PatientRegisterDTO;
import gr.aueb.cf.medicalcare.dto.patient.PatientUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.PatientMapper;
import gr.aueb.cf.medicalcare.model.Patient;
import gr.aueb.cf.medicalcare.security.JwtUtil;
import gr.aueb.cf.medicalcare.service.Patient.IPatientService;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService patientService;
    private final JwtUtil jwtUtil;

    /**
     * Fetches all patients
     * @return List of patients
     * @throws EntityNotFoundException
     */
    @GetMapping("/all")
    ResponseEntity<List<PatientReadOnlyDTO>> getAllPatients() throws EntityNotFoundException {
        List<PatientReadOnlyDTO> patientReadOnlyDTOs = new ArrayList<>();
        try {
            for (Patient patient : patientService.getPatients()) {
                patientReadOnlyDTOs.add(PatientMapper.mapPatientToPatientReadOnlyDTO(patient));
            }
            return ResponseEntity.ok(patientReadOnlyDTOs);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    /**
     * Fetches all patients by doctor's username
     * @param username  Doctor's username
     * @return          List of patients
     * @throws EntityNotFoundException
     */
    @GetMapping("/doctor/{username}")
    ResponseEntity<List<PatientReadOnlyDTO>> getPatientsByDoctorUsername(@PathVariable("username") String username)
            throws EntityNotFoundException {
        List<PatientReadOnlyDTO> patientReadOnlyDTOs = new ArrayList<>();
        try {
            for (Patient patient : patientService.getPatientsByDoctorUsername(username)) {
                patientReadOnlyDTOs.add(PatientMapper.mapPatientToPatientReadOnlyDTO(patient));
            }
            return ResponseEntity.ok(patientReadOnlyDTOs);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
    /**
     * Registers a patient
     * @param patientRegisterDTO    PatientRegisterDTO
     * @param bindingResult         BindingResult
     * @param authorizationHeader   Authorization Header
     * @return                      PatientReadOnlyDTO
     * @throws EntityAlreadyExistsException     If patient already exists
     */
    @PostMapping("/register")
    ResponseEntity<PatientReadOnlyDTO> registerPatient(@Valid @RequestBody PatientRegisterDTO patientRegisterDTO,
                                                       BindingResult bindingResult,
                                                       @RequestHeader(name = "Authorization", required = false)
                                                       String authorizationHeader) throws EntityAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        try {
            String doctorsUsername = jwtUtil.extractUsername(authorizationHeader.substring(7));
            Patient patient = patientService.registerPatient(patientRegisterDTO, doctorsUsername);
            return ResponseEntity.ok(PatientMapper.mapPatientToPatientReadOnlyDTO(patient));
        } catch (EntityAlreadyExistsException e) {
            throw e;
        }
    }

    /**
     * Update a patient
     * @param patientUpdateDTO      PatientUpdateDTO
     * @param bindingResult         BindingResult
     * @return                      PatientReadOnlyDTO
     * @throws EntityNotFoundException     If patient not found
     */
    @PutMapping("/update")
    ResponseEntity<PatientReadOnlyDTO> updatePatient(@Valid @RequestBody PatientUpdateDTO patientUpdateDTO,
                                                     BindingResult bindingResult) throws EntityNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        try {
            Patient patient = patientService.updatePatient(patientUpdateDTO);
            return ResponseEntity.ok(PatientMapper.mapPatientToPatientReadOnlyDTO(patient));
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    /**
     * Delete a patient
     * @param id    Patient ID
     * @return      ResponseMessage
     * @throws EntityNotFoundException     If patient not found
     */
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) throws EntityNotFoundException {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
}
