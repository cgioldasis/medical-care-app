package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.ResponseMessage;
import gr.aueb.cf.medicalcare.dto.speciality.SpecialityReadOnlyDTO;
import gr.aueb.cf.medicalcare.model.Specialization;
import gr.aueb.cf.medicalcare.service.Speciality.ISpecialityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/specialization")
@RequiredArgsConstructor
public class SpecializationController {

    private final ISpecialityService specialityService;

    @GetMapping("/all")
    public ResponseEntity<List<SpecialityReadOnlyDTO>> getAllSpecialities() {
        List<SpecialityReadOnlyDTO> readOnlyDTOS = new ArrayList<>();

        for (Specialization specialization : specialityService.getAllSpecialities()) {
            readOnlyDTOS.add(new SpecialityReadOnlyDTO(specialization.getSpecializationName()));
        }
        return ResponseEntity.ok(readOnlyDTOS);
    }
}
