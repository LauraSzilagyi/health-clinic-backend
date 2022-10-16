package ro.fasttrackit.proiect.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.proiect.appointment.entity.Appointment;
import ro.fasttrackit.proiect.appointment.mapper.AppointmentMapper;
import ro.fasttrackit.proiect.appointment.model.AppointmentModel;
import ro.fasttrackit.proiect.appointment.model.AppointmentResponseModel;
import ro.fasttrackit.proiect.appointment.repository.AppointmentRepository;
import ro.fasttrackit.proiect.doctor.entity.Doctor;
import ro.fasttrackit.proiect.doctor.service.DoctorService;
import ro.fasttrackit.proiect.email.service.EmailService;
import ro.fasttrackit.proiect.exceptions.InvalidRequestException;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static ro.fasttrackit.proiect.util.StringValidator.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository repository;
    private final DoctorService doctorService;
    private final EmailService emailService;
    private final AppointmentMapper mapper;

    @Transactional
    public AppointmentResponseModel addNewAppointment(AppointmentModel model) {
        validateModel(model);
        Doctor doctor = doctorService.findById(model.getDoctorId());
        Appointment appointment = mapper.mapModelToEntity(model, doctor);
        Appointment save = repository.save(appointment);

        sendEmailBasedOnAppointmentData(save);
        return AppointmentResponseModel.builder().status("success").build();

    }

    private void sendEmailBasedOnAppointmentData(Appointment save) {
        emailService.sendEmail(save.getEmail(), mapper.createEmailTemplateModel(save));
    }

    private void validateModel(AppointmentModel model) {
        validateName(model.getName());
        validateDepartment(model.getDepartment());
        validateEmail(model.getEmail());
        validateDate(model.getDate());
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Date is not valid!");
        }
    }
}
