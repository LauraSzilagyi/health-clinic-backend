package ro.fasttrackit.proiect.appointment.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ro.fasttrackit.proiect.appointment.entity.Appointment;
import ro.fasttrackit.proiect.appointment.model.AppointmentEmailModel;
import ro.fasttrackit.proiect.appointment.model.AppointmentModel;
import ro.fasttrackit.proiect.doctor.entity.Doctor;
import ro.fasttrackit.proiect.doctor.enums.Department;

import java.util.Map;

@Component
public class AppointmentMapper {

    public Appointment mapModelToEntity(AppointmentModel model, Doctor doctor) {
        return Appointment.builder()
                .department(Department.valueOf(model.getDepartment().toUpperCase()))
                .name(model.getName())
                .email(model.getEmail())
                .date(model.getDate())
                .time(model.getTime())
                .doctor(doctor)
                .build();
    }

    public Map<String, Object> createEmailTemplateModel(Appointment appointment) {
        AppointmentEmailModel appointmentEmailModel = AppointmentEmailModel.builder()
                .name(appointment.getName())
                .date(appointment.getDate().toString())
                .time(appointment.getTime().toString())
                .department(appointment.getDepartment().name())
                .doctor(appointment.getDoctor().getName())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(appointmentEmailModel, Map.class);
    }
}
