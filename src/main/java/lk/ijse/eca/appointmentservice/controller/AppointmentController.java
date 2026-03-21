package lk.ijse.eca.appointmentservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lk.ijse.eca.appointmentservice.dto.AppointmentDTO;
import lk.ijse.eca.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AppointmentDTO> createAppointment(
            @Valid @RequestBody AppointmentDTO dto
    ){
        log.info("POST /api/v1/appointments - Creating appointment for patient: {} and doctor: {}", dto.getPatientId(), dto.getDoctorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(dto));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDTO> getAppointment(
            @PathVariable @Positive(message = "Appointment ID must be a positive number") Long id) {
        log.info("GET /api/v1/appointment/{}", id);
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        log.info("GET /api/v1/appointment - retrieving all appointment");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping(params = "doctorId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctorId(
            @RequestParam @NotBlank(message = "Doctor ID must not be blank") String doctorId) {
        log.info("GET /api/v1/appointment?doctorId={} - retrieving appointment by doctor", doctorId);
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorId(doctorId));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @PathVariable @Positive(message = "Appointment ID must be a positive number") Long id,
            @Valid @RequestBody AppointmentDTO dto) {
        log.info("PUT /api/v1/appointment/{}", id);
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable @Positive(message = "Appointment ID must be a positive number") Long id) {
        log.info("DELETE /api/v1/appointment/{}", id);
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }


}
