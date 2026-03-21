package lk.ijse.eca.appointmentservice.mapper;

import lk.ijse.eca.appointmentservice.dto.AppointmentDTO;
import lk.ijse.eca.appointmentservice.dto.PatientDTO;
import lk.ijse.eca.appointmentservice.entity.Appointment;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AppointmentMapper {

    @Mapping(target = "patient",source = "patient")
    AppointmentDTO toDto(Appointment appointment, PatientDTO patient);

    @Mapping(target = "id", ignore = true)
    Appointment toEntity(AppointmentDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",ignore = true)
    void updateEntity(AppointmentDTO dto, @MappingTarget Appointment appointment);
}
