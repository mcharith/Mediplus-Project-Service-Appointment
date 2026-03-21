package lk.ijse.eca.appointmentservice.service.impl;

import lk.ijse.eca.appointmentservice.client.DoctorServiceClient;
import lk.ijse.eca.appointmentservice.client.PatientServiceClient;
import lk.ijse.eca.appointmentservice.dto.AppointmentDTO;
import lk.ijse.eca.appointmentservice.dto.PatientDTO;
import lk.ijse.eca.appointmentservice.entity.Appointment;
import lk.ijse.eca.appointmentservice.exception.AppointmentNotFoundException;
import lk.ijse.eca.appointmentservice.mapper.AppointmentMapper;
import lk.ijse.eca.appointmentservice.repository.AppointmentRepository;
import lk.ijse.eca.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientServiceClient patientServiceClient;
    private final DoctorServiceClient doctorServiceClient;


    @Override
    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        log.debug("Creating appointment for patient: {} and doctor: {}", dto.getPatientId(), dto.getDoctorId());

        PatientDTO patient = patientServiceClient.getPatient(dto.getPatientId());
        doctorServiceClient.validateDoctor(dto.getDoctorId());

        Appointment appointment = appointmentMapper.toEntity(dto);
        Appointment saved = appointmentRepository.save(appointment);
        log.info("Enew appointment created: {}", saved.getId());
        return appointmentMapper.toDto(saved, patient);
    }

    @Override
    @Transactional
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        log.debug("Updating appointment with ID {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Enrollment not found for update: {}", id);
                    return new AppointmentNotFoundException(id);
                });

        PatientDTO patient = patientServiceClient.getPatient(dto.getPatientId());
        doctorServiceClient.validateDoctor(dto.getDoctorId());

        appointmentMapper.updateEntity(dto,appointment);
        Appointment updated = appointmentRepository.save(appointment);
        log.info("Appointment updated: {}", id);
        return appointmentMapper.toDto(updated, patient);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        log.debug("Deleting appointment with ID: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Appointment not found for deletion: {}", id);
                    return new AppointmentNotFoundException(id);
                });

        appointmentRepository.delete(appointment);
        log.info("Appointment deleted successfully: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentDTO getAppointment(Long id) {
        log.debug("Fetching Appointment with ID: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Appointment not found: {}", id);
                    return new AppointmentNotFoundException(id);
                });

        PatientDTO patient = patientServiceClient.getPatient(appointment.getPatientId());
        log.info("Appointment fetched successfully: {}", id);
        return appointmentMapper.toDto(appointment, patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointments() {
        log.debug("Fetching all appointments");

        List<AppointmentDTO> appointments = appointmentRepository.findAll()
                .stream()
                .map(enrollment -> {
                    PatientDTO patient = patientServiceClient.getPatient(enrollment.getPatientId());
                    return appointmentMapper.toDto(enrollment, patient);
                })
                .toList();

        log.debug("Fetched {} appointments", appointments.size());
        return appointments;
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctorId(String doctorId) {
        log.debug("Fetching appointment for doctorId: {}", doctorId);

        List<AppointmentDTO> appointments = appointmentRepository.findByDoctorIdOrderByDateDescIdDesc(doctorId)
                .stream()
                .map(appointment -> {
                    PatientDTO patient = patientServiceClient.getPatient(appointment.getPatientId());
                    return appointmentMapper.toDto(appointment, patient);
                })
                .toList();

        log.debug("Fetched {} appointment for doctorId: {}", appointments.size(), doctorId);
        return appointments;
    }
}
