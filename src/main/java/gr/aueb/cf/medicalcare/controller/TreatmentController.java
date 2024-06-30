package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.treatment.TreatmentReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentRegisterDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentUpdateDTO;
import gr.aueb.cf.medicalcare.dto.user.UserReadOnlyDTO;
import gr.aueb.cf.medicalcare.mapper.TreatmentMapper;
import gr.aueb.cf.medicalcare.model.Treatment;
import gr.aueb.cf.medicalcare.security.JwtUtil;
import gr.aueb.cf.medicalcare.service.Treatment.ITreatmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/treatment")
@RequiredArgsConstructor
public class TreatmentController {
    // Treatment Service injection
    private final ITreatmentService treatmentService;
    private final JwtUtil jwtUtil;

    /**
     * Get all Treatment
     * @return                              List of medicines
     * @throws EntityNotFoundException      No medicine found
     */
    @Operation(summary = "Get all medicines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treatment Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "No treatment found",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<Set<TreatmentReadOnlyDTO>> getAllTreatments() throws EntityNotFoundException {
        Set<TreatmentReadOnlyDTO> readOnlyDTOS = new HashSet<>();
        try {
            for (Treatment treatment : treatmentService.getAllTreatments()) {
                readOnlyDTOS.add(TreatmentMapper.mapTreatmentToTreatmentReadOnlyDTO(treatment));
            }
            return ResponseEntity.ok(readOnlyDTOS);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
    @GetMapping("/patient/{ssid}")
    public ResponseEntity<TreatmentReadOnlyDTO> getTreatmentByPatientSsid(@PathVariable("ssid") String patientSsid) throws
            EntityNotFoundException {
        Treatment treatment;
        try {
            treatment = treatmentService.getTreatmentByPatientSSID(patientSsid);
            return ResponseEntity.ok(TreatmentMapper.mapTreatmentToTreatmentReadOnlyDTO(treatment));
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TreatmentReadOnlyDTO> insertTreatment(@Valid @RequestBody TreatmentRegisterDTO treatmentRegisterDTO,
                                                                BindingResult bindingResult,
                                                                @RequestHeader(name = "Authorization", required = false)
                                                                String authorizationHeader)
                                                                throws EntityNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        try {
            String doctorsUsername = jwtUtil.extractUsername(authorizationHeader.substring(7));
            Treatment treatment = treatmentService.insertTreatment(treatmentRegisterDTO, doctorsUsername);
            return ResponseEntity.ok(TreatmentMapper.mapTreatmentToTreatmentReadOnlyDTO(treatment));
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TreatmentReadOnlyDTO> updateTreatment(@PathVariable("id") Long id, @RequestBody TreatmentUpdateDTO treatmentUpdateDTO) throws EntityNotFoundException {
        try {
            Treatment treatment = treatmentService.updateTreatment(treatmentUpdateDTO);
            return ResponseEntity.ok(TreatmentMapper.mapTreatmentToTreatmentReadOnlyDTO(treatment));
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable("id") Long id) throws EntityNotFoundException {
        try {
            treatmentService.deleteTreatment(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

}
