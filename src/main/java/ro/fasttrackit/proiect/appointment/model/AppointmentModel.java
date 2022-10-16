package ro.fasttrackit.proiect.appointment.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentModel {
    private String department;
    private Integer doctorId;
    private String name;
    private String email;
    private LocalDate date;
    @JsonFormat(pattern = "H:mm")
    private LocalTime time;
}
