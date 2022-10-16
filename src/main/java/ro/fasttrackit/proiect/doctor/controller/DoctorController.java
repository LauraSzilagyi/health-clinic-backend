package ro.fasttrackit.proiect.doctor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.proiect.doctor.model.*;
import ro.fasttrackit.proiect.doctor.enums.Department;
import ro.fasttrackit.proiect.doctor.service.DoctorService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService service;

    @GetMapping
    public List<DoctorModel> findAllDoctors() {
        return service.findAllDoctors();
    }

    @GetMapping("/{doctorId}/availability")
    public List<DoctorAvailabilityModel> findAvailability(@PathVariable Integer doctorId) {
        return service.findDoctorAvailabilityByDoctorId(doctorId);
    }

    @GetMapping("/departments/{department}")
    public List<DoctorModel> findDoctorsByDepartmentId(@PathVariable Department department) {
        return service.findDoctorsByDepartmentId(department);
    }

    @GetMapping("departments")
    public List<Department> findAllDepartments() {
        return Arrays.stream(Department.values()).toList();
    }

    @GetMapping("/{doctorId}")
    public DoctorModel findDoctorById(@PathVariable Integer doctorId) {
        return service.findDoctorById(doctorId);
    }

    @PatchMapping("/{doctorId}/image")
    public DoctorModel changeDoctorImage(@PathVariable Integer doctorId, @RequestBody ImageModel model){
        return service.changeImage(doctorId, model);
    }

    @GetMapping("/{doctorId}/appointments")
    public List<DaysSchedule> getAppointmentsByDoctorId(@PathVariable Integer doctorId) {
        return service.getAppointmentsByDoctorId(doctorId);
    }

    @PostMapping()
    public DoctorModel addNewDoctor(@RequestBody NewDoctorModel model) {
        return service.addNewDoctor(model);
    }

    @PatchMapping("/{doctorId}")
    DoctorModel updateDoctor(@PathVariable Integer doctorId, @RequestBody UpdateDoctorModel model){
        return service.updateDoctor(doctorId, model);
    }

    @DeleteMapping("{doctorId}")
    public void deleteDoctorById(@PathVariable Integer doctorId) {
        service.deleteDoctorById(doctorId);
    }

    @PostMapping("{doctorId}/availability")
    public void addDoctorAvailability(@PathVariable Integer doctorId, @RequestBody List<WorkingPeriod> periods) {
        service.addDoctorAvailabilities(doctorId, periods);
    }
}
