package gr.aueb.cf.medicalcare.controller;

import gr.aueb.cf.medicalcare.dto.doctor.DoctorReadOnlyDTO;
import gr.aueb.cf.medicalcare.dto.doctor.DoctorRegisterDTO;
import gr.aueb.cf.medicalcare.mapper.DoctorMapper;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.service.DoctorServiceImpl;
import gr.aueb.cf.medicalcare.service.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorServiceImpl doctorService;


    @PostMapping("/doctor")
    public ResponseEntity<DoctorReadOnlyDTO> registerNewDoctor(@RequestBody DoctorRegisterDTO dto) throws EntityAlreadyExistsException {
        Doctor doctor= doctorService.registerNewDoctor(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(doctor.getId())
                .toUri();
        return ResponseEntity.created(location).body(DoctorMapper.extractDoctorReadOnlyDTOFromDoctorRegisterDTO(doctor));

    }
}
