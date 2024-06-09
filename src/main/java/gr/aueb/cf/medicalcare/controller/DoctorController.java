package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorRegisterDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.DoctorMapper;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.service.Doctor.IDoctorService;
import gr.aueb.cf.medicalcare.service.exception.DoctorNotFoundException;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import gr.aueb.cf.medicalcare.service.exception.UserNotFoundException;
import gr.aueb.cf.medicalcare.validator.DoctorRegisterValidator;
import gr.aueb.cf.medicalcare.validator.DoctorUpdateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    // Injecting the DoctorService
    private final IDoctorService doctorService;
    // Injecting the DoctorRegisterValidator
    private final DoctorRegisterValidator doctorRegisterValidator;
    // Injecting the DoctorUpdateValidator
    private final DoctorUpdateValidator doctorUpdateValidator;

    /**
     * Endpoint to get a doctor by username
     * @param username                  The username of the doctor
     * @return                          The doctor
     * @throws UserNotFoundException    If the doctor with username is not found
     */
    @Operation(summary = "Get a doctor by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/username/{username}")
    public ResponseEntity<DoctorReadOnlyDTO> getDoctorByUserUsername(@PathVariable("username") String username)
            throws UserNotFoundException {
        try {
            Doctor doctor = doctorService.getDoctorByUserUsername(username);
            return ResponseEntity.ok(DoctorMapper.extractDoctorReadOnlyDTOFromDoctor(doctor));
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

    /**
     * Endpoint to get all doctors.
     * @return                              The list of doctors
     * @throws DoctorNotFoundException      If the list of doctors is empty
     */
    @Operation(summary = "Get all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctors not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<DoctorReadOnlyDTO>> getAllDoctors() throws DoctorNotFoundException {
        List<Doctor> doctors;
        try {
            doctors = doctorService.getAllDoctors();
            List<DoctorReadOnlyDTO> doctorReadOnlyDTOS = new ArrayList<>();
            for (Doctor doctor : doctors) {
                doctorReadOnlyDTOS.add(DoctorMapper.extractDoctorReadOnlyDTOFromDoctor(doctor));
            }
            return ResponseEntity.ok(doctorReadOnlyDTOS);
        } catch (DoctorNotFoundException e) {
            throw e;
        }
    }

    /**
     * Endpoint to get doctors by specialization
     * @param specializationName            The specialization of the doctor
     * @return                              The list of doctors
     * @throws DoctorNotFoundException      If the list of doctors is empty
     */
    @Operation(summary = "Get doctors by specialization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctors not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/specialization/{speciality}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable("speciality")
                                                                   String specializationName)
            throws DoctorNotFoundException {
        List<Doctor> doctors;
        try {
            doctors = doctorService.getDoctorsBySpecialization(specializationName);
            return ResponseEntity.ok(doctors);
        } catch (DoctorNotFoundException e) {
            throw e;
        }
    }

    /**
     * Endpoint to get doctors by lastname
     * @param lastname                      The lastname of the doctor
     * @return                              The list of doctors
     * @throws DoctorNotFoundException      If the list of doctors is empty
     */
    @Operation(summary = "Get doctors by lastname")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctors not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<DoctorReadOnlyDTO>> getDoctorsByLastname(@PathVariable("lastname") String lastname)
            throws DoctorNotFoundException {
        List<Doctor> doctors;
        try {
            doctors = doctorService.getDoctorsByLastname(lastname);
            List<DoctorReadOnlyDTO> doctorReadOnlyDTOS = new ArrayList<>();
            for (Doctor doctor : doctors) {
                doctorReadOnlyDTOS.add(DoctorMapper.extractDoctorReadOnlyDTOFromDoctor(doctor));
            }
            return ResponseEntity.ok(doctorReadOnlyDTOS);
        } catch (DoctorNotFoundException e) {
            throw e;
        }
    }

    @GetMapping("/ssid/{ssid}")
    public ResponseEntity<DoctorReadOnlyDTO> getDoctorBySsid(@PathVariable("ssid") String ssid)
            throws DoctorNotFoundException {
        try {
            Doctor doctor = doctorService.getDoctorBySsid(ssid);
            return ResponseEntity.ok(DoctorMapper.extractDoctorReadOnlyDTOFromDoctor(doctor));
        } catch (DoctorNotFoundException e) {
            throw e;
        }
    }

    /**
     * Endpoint to register a new doctor
     * @param dto                           The doctor register dto
     * @param bindingResult                 The binding result
     * @return                              The doctor
     * @throws EntityAlreadyExistsException If the doctor already exists
     */
    @Operation(summary = "Add a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/register")
    public ResponseEntity<DoctorReadOnlyDTO> registerNewDoctor(
            @Valid @RequestBody DoctorRegisterDTO dto, BindingResult bindingResult) throws EntityAlreadyExistsException {
        doctorRegisterValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Doctor doctor= doctorService.registerNewDoctor(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(doctor.getId())
                .toUri();
        return ResponseEntity.created(location).body(DoctorMapper.extractDoctorReadOnlyDTOFromDoctor(doctor));

    }

    /**
     * Endpoint to update a doctor
     * @param id                            The id of the doctor
     * @param dto                           The doctor update dto
     * @param bindingResult                 The binding result
     * @return                              The doctor
     * @throws DoctorNotFoundException       If the doctor is not found
     */
    @Operation(summary = "Update a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PutMapping("/update/{id}")
    public ResponseEntity<DoctorReadOnlyDTO> updateDoctor(@PathVariable("id") Long id,
                                                          @Valid @RequestBody DoctorUpdateDTO dto,
                                                          BindingResult bindingResult)
            throws EntityAlreadyExistsException, DoctorNotFoundException {
        doctorUpdateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (!id.equals(dto.getId())) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
        Doctor doctor = doctorService.updateDoctor(dto);
        return ResponseEntity.ok(DoctorMapper.extractDoctorReadOnlyDTOFromDoctor(doctor));
        } catch (DoctorNotFoundException e) {
            throw e;
        }
    }

    @Operation(summary = "Delete a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DoctorReadOnlyDTO> deleteDoctor(@PathVariable("id") Long id) throws DoctorNotFoundException {
        try {
            Doctor doctor = doctorService.deleteDoctor(id);
            return ResponseEntity.ok(DoctorMapper.extractDoctorReadOnlyDTOFromDoctor(doctor));
        } catch (DoctorNotFoundException e) {
            throw e;
        }
    }

    /**
     * Endpoint to count all doctors
     * @return                              The number of doctors
     */
    @Operation(summary = "Count all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors counted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)) }),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/count/all")
    public ResponseEntity<Long> countAllDoctors() {
        return ResponseEntity.ok(doctorService.countAllDoctors());
    }

    /**
     * Endpoint to count doctors by specialization
     * @param specializationName            The specialization of the doctor
     * @return                              The number of doctors
     */
    @Operation(summary = "Count doctors by specialization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors counted with specific specialization",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)) }),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @GetMapping("/count/{specializationName}")
    public ResponseEntity<Long> countDoctorsBySpecialization(@PathVariable("specializationName")
                                                                 String specializationName)      {
        return ResponseEntity.ok(doctorService.countDoctorsBySpecialization(specializationName));
    }
}
