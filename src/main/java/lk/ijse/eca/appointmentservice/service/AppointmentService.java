package lk.ijse.eca.appointmentservice.service;

import lk.ijse.eca.appointmentservice.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO dto);

    AppointmentDTO updateAppointment(Long id, AppointmentDTO dto);

    void deleteAppointment(Long id);

    AppointmentDTO getAppointment(Long id);

    List<AppointmentDTO> getAllAppointments();

    List<AppointmentDTO> getAppointmentsByDoctorId(String doctorId);
}
