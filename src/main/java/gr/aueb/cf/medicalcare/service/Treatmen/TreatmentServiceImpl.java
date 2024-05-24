package gr.aueb.cf.medicalcare.service.Treatmen;

import gr.aueb.cf.medicalcare.dto.treatment.TreatmentRegisterDTO;
import gr.aueb.cf.medicalcare.dto.treatment.TreatmentUpdateDTO;
import gr.aueb.cf.medicalcare.mapper.TreatmentMapper;
import gr.aueb.cf.medicalcare.model.Doctor;
import gr.aueb.cf.medicalcare.model.Medicine;
import gr.aueb.cf.medicalcare.model.Treatment;
import gr.aueb.cf.medicalcare.repository.DoctorRepository;
import gr.aueb.cf.medicalcare.repository.MedicineRepository;
import gr.aueb.cf.medicalcare.repository.TreatmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TreatmentServiceImpl implements ITreatmentService{

    // Injecting the TreatmentRepository
    private final TreatmentRepository treatmentRepository;
    private final DoctorRepository doctorRepository;
    private final MedicineRepository medicineRepository;


    @Override
    public Treatment getTreatmentByName(String treatmentName) {
        Treatment treatment;
        try {
            // Return the treatment if found, otherwise throw an exception
            treatment = treatmentRepository.findByTreatmentName(treatmentName).orElseThrow(() ->
                    new EntityNotFoundException("Treatment with name: " + treatmentName + " not found"));
            log.info("Treatment with name: {} found", treatmentName);
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Treatment getTreatmentById(Long id) {
        Treatment treatment;
        try {
            // Return the treatment if found, otherwise throw an exception
            treatment = treatmentRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Treatment with id: " + id + " not found"));
            log.info("Treatment with id: {} found", id);
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Treatment> getTreatmentsByMedicineName(String medicineName) {
        List<Treatment> treatments = new ArrayList<>();
        try {
            treatments = treatmentRepository.findTreatmentsByMedicineName(medicineName);
            if (treatments.isEmpty()) {
                throw new EntityNotFoundException("Treatments with medicine " + medicineName + " not found");
            }
            log.info("Treatments with medicine {} found", medicineName);
            return treatments
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Treatment> getTreatmentsByDoctorPersonalDetailsLastname(String ssid) {
        List<Treatment> treatments = new ArrayList<>();
        try {
            treatments = treatmentRepository.findTreatmentsByDoctorPersonalDetailsLastname(ssid);
            if (treatments.isEmpty()) {
                throw new EntityNotFoundException("Treatments with doctor " + ssid + " not found");
            }
            log.info("Treatments with doctor {} found", ssid);
            return treatments;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Treatment> getAllTreatments() {
        List<Treatment> treatments = new ArrayList<>();
        try {
            treatments = treatmentRepository.findAll();
            if (treatments.isEmpty()) {
                throw new EntityNotFoundException("No treatments found");
            }
            log.info("All treatments found");
            return treatments;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public Treatment insertTreatment(TreatmentRegisterDTO dto) {
        Treatment treatment;
        Doctor doctor;
        Medicine medicine;
        try {
            doctor = doctorRepository.findDoctorByPersonalDetailsSsid(dto.getDoctorSsid()).orElseThrow(() ->
                    new EntityNotFoundException("Doctor with ssid: " + dto.getDoctorSsid() + " not found"));
            treatment = TreatmentMapper.extractTreatmentFromDTO(dto);
            for (String medicineName : dto.getMedicineNames()) {
                medicine = medicineRepository.findMedicineByMedicineName(medicineName).orElseThrow(() ->
                        new EntityNotFoundException("Medicine with name: " + medicineName + " not found"));
                treatment.addMedicine(medicine);
            }
            treatment.addDoctor(doctor);
            treatmentRepository.save(treatment);
            log.info("Treatment with name: {} was added at {}", treatment.getTreatmentName(), LocalDateTime.now());
            return treatment;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Treatment updateTreatment(TreatmentUpdateDTO dto) {
        return null;
    }

    @Override
    public Treatment deleteTreatment(Long id) {
        return null;
    }
}
