package ro.fasttrackit.proiect.appointment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.proiect.appointment.model.AppointmentModel;
import ro.fasttrackit.proiect.appointment.model.AppointmentResponseModel;
import ro.fasttrackit.proiect.appointment.service.AppointmentService;

@RestController
@RequestMapping("appointment")
@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService service;

    @PostMapping()
    AppointmentResponseModel addNewAppointment(@RequestBody AppointmentModel model) {
        return service.addNewAppointment(model);
    }
}
