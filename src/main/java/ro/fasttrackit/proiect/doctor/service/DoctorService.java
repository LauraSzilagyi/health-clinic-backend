package ro.fasttrackit.proiect.doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.proiect.doctor.entity.Doctor;
import ro.fasttrackit.proiect.doctor.entity.DoctorAvailability;
import ro.fasttrackit.proiect.doctor.enums.Department;
import ro.fasttrackit.proiect.doctor.mapper.DoctorMapper;
import ro.fasttrackit.proiect.doctor.model.*;
import ro.fasttrackit.proiect.doctor.repository.DoctorAvailabilityRepository;
import ro.fasttrackit.proiect.doctor.repository.DoctorRepository;
import ro.fasttrackit.proiect.exceptions.EntityNotFoundException;

import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static ro.fasttrackit.proiect.util.DateUtil.get30DaysDate;
import static ro.fasttrackit.proiect.util.DateUtil.getWeekDays;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository repository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorMapper mapper;

    public List<DoctorModel> findAllDoctors() {
        List<Doctor> all = repository.findAll();
        return all.stream()
                .filter(d -> Boolean.FALSE.equals(d.getIsAdmin()))
                .map(mapper::mapToDoctorModel)
                .toList();
    }

    public List<DoctorModel> findDoctorsByDepartmentId(Department department) {
        List<Doctor> all = repository.findAllByDepartment(department);
        return all.stream()
                .map(mapper::mapToDoctorModel)
                .toList();
    }

    public List<DoctorAvailabilityModel> findDoctorAvailabilityByDoctorId(Integer doctorId) {
        Doctor doctor = findById(doctorId);
        List<LocalDate> daysDate = get30DaysDate();

        return daysDate.stream()
                .map(localDate -> mapper.mapToAvailabilityModel(localDate, doctor))
                .toList();
    }

    public Doctor findById(Integer doctorId) {
        return repository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id %s doesn't exists!".formatted(doctorId)));
    }

    public DoctorModel findDoctorById(Integer doctorId) {
        Doctor doctor = findById(doctorId);
        return mapper.mapToDoctorModel(doctor);
    }

    public DoctorModel changeImage(Integer doctorId, ImageModel model) {
        Doctor doctor = findById(doctorId);
        doctor.setImage(model.image().getBytes(StandardCharsets.UTF_8));
        repository.save(doctor);
        return mapper.mapToDoctorModel(doctor);
    }


    public List<DaysSchedule> getAppointmentsByDoctorId(Integer doctorId) {
        List<DaysSchedule> result = new ArrayList<>();

        getWeekDays().forEach(w -> { // for each working days get appointments (MONDAY <-> FRIDAY)
            DaysSchedule daySchedule = new DaysSchedule();
            daySchedule.setDay(DayOfWeek.valueOf(w.getDayOfWeek().name()));

            Optional<Doctor> doctorById = repository.findById(doctorId);
            doctorById.ifPresent(doctor -> doctor.getAppointments().stream()
                    .filter(a -> a.getDate().isEqual(w))
                    .map(mapper::mapAppointmentToScheduleModel)
                    .forEach(daySchedule::addSchedule));
            result.add(daySchedule);
        });

        return result;
    }

    public DoctorModel addNewDoctor(NewDoctorModel model) {
        Doctor doctor = mapper.mapToDoctorEntity(model);
        repository.save(doctor);
        return mapper.mapToDoctorModel(doctor);
    }

    public void deleteDoctorById(Integer doctorId) {
        Doctor doctor = findById(doctorId);
        repository.delete(doctor);
    }

    public DoctorModel updateDoctor(Integer doctorId, UpdateDoctorModel model) {
        Doctor doctor = findById(doctorId);

        ofNullable(model.getName()).ifPresent(doctor::setName);
        ofNullable(model.getEmail()).ifPresent(doctor::setEmail);
        ofNullable(model.getPassword()).ifPresent(doctor::setPassword);
        ofNullable(model.getAddress()).ifPresent(doctor::setAddress);
        setTheImage(model, doctor);
        ofNullable(model.getPhone()).ifPresent(doctor::setPhone);
        ofNullable(model.getDescription()).ifPresent(doctor::setDescription);
        ofNullable(model.getDepartment()).ifPresent(doctor::setDepartment);
        ofNullable(model.getSpecialist()).ifPresent(doctor::setSpecialist);
        ofNullable(model.getExperience()).ifPresent(doctor::setExperience);
        ofNullable(model.getBirthdate()).ifPresent(b -> doctor.setBirthdate(LocalDate.parse(b)));

        repository.save(doctor);
        return mapper.mapToDoctorModel(doctor);
    }

    private void setTheImage(UpdateDoctorModel model, Doctor doctor) {
        if (ofNullable(model.getImage()).isPresent()) {
            doctor.setImage(model.getImage().getBytes(StandardCharsets.UTF_8));
        }
    }

    public void addDoctorAvailabilities(Integer doctorId, List<WorkingPeriod> periods) {
        Doctor doctor = findById(doctorId);
        List<DoctorAvailability> doctorAvailabilities = mapper.mapToDoctorAvailabilities(periods);
        List<DoctorAvailability> savedAvailabilities = doctorAvailabilityRepository.saveAll(doctorAvailabilities);
        doctor.setAvailabilities(savedAvailabilities);
        repository.save(doctor);
    }
}
