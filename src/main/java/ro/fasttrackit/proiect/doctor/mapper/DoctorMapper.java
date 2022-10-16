package ro.fasttrackit.proiect.doctor.mapper;

import org.springframework.stereotype.Component;
import ro.fasttrackit.proiect.appointment.entity.Appointment;
import ro.fasttrackit.proiect.doctor.entity.Doctor;
import ro.fasttrackit.proiect.doctor.entity.DoctorAvailability;
import ro.fasttrackit.proiect.doctor.model.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class DoctorMapper {

    public DoctorModel mapToDoctorModel(Doctor doctor) {
        return DoctorModel.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .description(doctor.getDescription())
                .specialist(doctor.getSpecialist())
                .image(new String(doctor.getImage(), StandardCharsets.UTF_8))
                .email(doctor.getEmail())
                .password(doctor.getPassword())
                .birthdate(ofNullable(doctor.getBirthdate()).map(LocalDate::toString).orElse(null))
                .address(doctor.getAddress())
                .phone(doctor.getPhone())
                .department(doctor.getDepartment())
                .experience(doctor.getExperience())
                .availabilities(mapToWorkingPeriods(doctor.getAvailabilities()))
                .build();
    }

    public Doctor mapToDoctorEntity(NewDoctorModel model) {
        Doctor doctor = new Doctor();
        doctor.setName(model.getName());
        doctor.setEmail(model.getEmail());
        doctor.setPassword(model.getPassword());
        doctor.setIsAdmin(false);
        doctor.setBirthdate(LocalDate.parse(model.getBirthdate()));
        doctor.setAddress(model.getAddress());
        doctor.setImage(model.getImage().getBytes(StandardCharsets.UTF_8));
        doctor.setPhone(model.getPhone());
        doctor.setDescription(model.getDescription());
        doctor.setDepartment(model.getDepartment());
        doctor.setSpecialist(model.getSpecialist());
        doctor.setExperience(model.getExperience());
        doctor.setAvailabilities(model.getAvailabilities());
        return doctor;
    }

    private List<WorkingPeriod> mapToWorkingPeriods(List<DoctorAvailability> availabilities) {
        return ofNullable(availabilities).orElse(new ArrayList<>()).stream()
                .map(this::mapToWorkingPeriod)
                .toList();
    }

    private WorkingPeriod mapToWorkingPeriod(DoctorAvailability doctorAvailability) {
        return WorkingPeriod.builder()
                .day(doctorAvailability.getDayOfWeek())
                .startTime(doctorAvailability.getStartDate())
                .endTime(doctorAvailability.getEndDate())
                .build();
    }

    public DoctorAvailabilityModel mapToAvailabilityModel(LocalDate localDate, Doctor doctor) {
        DoctorAvailabilityModel doctorAvailabilityResult = new DoctorAvailabilityModel();
        doctorAvailabilityResult.setDate(localDate.toString());
        Optional<DoctorAvailability> first = doctor.getAvailabilities().stream()
                .filter(doctorAvailability -> doctorAvailability.getDayOfWeek().toString().equals(localDate.getDayOfWeek().toString()))
                .findFirst();

        first.ifPresent(doctorAvailability -> doctorAvailabilityResult.setOpenInterval(PeriodModel.builder()
                .start(doctorAvailability.getStartDate())
                .end(doctorAvailability.getEndDate())
                .build()));

        List<PeriodModel> bookedSchedules = doctor.getAppointments().stream()
                .filter(appointment -> appointment.getDate().isEqual(localDate))
                .map(Appointment::getTime)
                .map(this::mapToBookedSchedules)
                .toList();
        doctorAvailabilityResult.setBookedSchedules(bookedSchedules);

        return doctorAvailabilityResult;
    }

    private PeriodModel mapToBookedSchedules(LocalTime localTime) {
        return PeriodModel.builder()
                .start(DateTimeFormatter.ofPattern("H:mm").format(localTime))
                .end(localTime.plusMinutes(30).toString())
                .build();
    }

    public ScheduleModel mapAppointmentToScheduleModel(Appointment appointment) {
        return ScheduleModel.builder()
                .name(appointment.getName())
                .dataStart(appointment.getTime().toString())
                .dataEnd(appointment.getTime().plusMinutes(30).toString())
                .build();
    }

    public List<DoctorAvailability> mapToDoctorAvailabilities(List<WorkingPeriod> periods) {
        return periods.stream()
                .map(this::mapToDoctorAvailabilityEntity)
                .toList();
    }

    private DoctorAvailability mapToDoctorAvailabilityEntity(WorkingPeriod workingPeriod) {
        return DoctorAvailability.builder()
                .dayOfWeek(workingPeriod.day())
                .startDate(workingPeriod.startTime())
                .endDate(workingPeriod.endTime())
                .build();
    }
}
