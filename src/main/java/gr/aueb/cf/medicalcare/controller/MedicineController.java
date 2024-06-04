package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.medicine.MedicineInsertDTO;
import gr.aueb.cf.medicalcare.dto.medicine.MedicineReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.medicine.MedicineUpdateDTO;
import gr.aueb.cf.medicalcare.dto.user.UserReadOnlyDTO;
import gr.aueb.cf.medicalcare.mapper.MedicineMapper;
import gr.aueb.cf.medicalcare.model.Medicine;
import gr.aueb.cf.medicalcare.service.Medicine.IMedicineService;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Medicine Controller
 */

@RestController
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
public class MedicineController {

    // Medicine Service injection
    private final IMedicineService medicineService;


    /**
     * Get a medicine by medicine name
     * @param medicineName                  Medicine name
     * @return                              Medicine
     * @throws EntityNotFoundException      Medicine not found
     */
    @Operation(summary = "Get a medicine by medicine name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicine Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "No medicine found",
                    content = @Content)})
    @GetMapping("/medicineName/{medicineName}")
    public ResponseEntity<MedicineReadOnlyDTO> getMedicineByMedicineName(
            @PathVariable("medicineName") String medicineName) throws EntityNotFoundException {
        try {
            Medicine medicine;
            medicine = medicineService.findMedicineByMedicineName(medicineName);
            MedicineReadOnlyDTO medicineReadOnlyDTO = MedicineMapper.mapMedicineToMedicineReadOnlyDTO(medicine);
            return ResponseEntity.ok(medicineReadOnlyDTO);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    /**
     * Get all medicines
     * @return                              List of medicines
     * @throws EntityNotFoundException      No medicine found
     */
    @Operation(summary = "Get all medicines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicine Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "No medicine found",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<Set<MedicineReadOnlyDTO>> getAllMedicines() {
        Set<MedicineReadOnlyDTO> readOnlyDTOS = new HashSet<>();
        try {
            for (Medicine medicine : medicineService.findAllMedicines()) {
                readOnlyDTOS.add(MedicineMapper.mapMedicineToMedicineReadOnlyDTO(medicine));
            }
            return ResponseEntity.ok(readOnlyDTOS);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    /**
     * Add a new medicine
     * @param dto                           Medicine DTO
     * @return                              Medicine
     * @throws EntityAlreadyExistsException Medicine already exists
     */
    @Operation(summary = "Add a new medicine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicine Created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicineReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Medicine already exists",
                    content = @Content)})
    @PostMapping("/add")
    public ResponseEntity<MedicineReadOnlyDTO> addMedicine(
            @Valid @RequestBody MedicineInsertDTO dto, BindingResult bindingResult) throws EntityAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Medicine medicine = medicineService.addMedicine(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(medicine.getId()).toUri();
        return ResponseEntity.created(location).body(MedicineMapper.mapMedicineToMedicineReadOnlyDTO(medicine));
    }


    /**
     * Update an existing medicine
     * @param id                            Medicine ID
     * @param dto                           Medicine DTO
     * @return                              Medicine
     * @throws EntityNotFoundException      Medicine not found
     */
    @Operation(summary = "Update an existing medicine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicine Updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicineReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Medicine not found",
                    content = @Content)})
    @PutMapping("/update/{id}")
    public ResponseEntity<MedicineReadOnlyDTO> updateMedicine(@PathVariable("id") Long id,
            @Valid @RequestBody MedicineUpdateDTO dto, BindingResult bindingResult) throws EntityAlreadyExistsException {

        if (!Objects.equals(id, dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        try {
            Medicine medicine = medicineService.updateMedicine(dto);
            MedicineReadOnlyDTO medicineReadOnlyDTO = MedicineMapper.mapMedicineToMedicineReadOnlyDTO(medicine);
            return ResponseEntity.ok(medicineReadOnlyDTO);
        } catch (EntityNotFoundException e) {
            throw e;
        }

    }

    /**
     * Delete a medicine
     * @param id                            Medicine ID
     * @throws EntityNotFoundException      Medicine not found
     */
    @Operation(summary = "Delete a medicine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicine Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicineReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Medicine not found",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable("id") Long id) {
        try {
            medicineService.deleteMedicine(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
}

