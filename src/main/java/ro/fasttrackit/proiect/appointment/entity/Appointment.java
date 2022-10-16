package ro.fasttrackit.proiect.appointment.entity;

import lombok.*;
import ro.fasttrackit.proiect.doctor.entity.Doctor;
import ro.fasttrackit.proiect.doctor.enums.Department;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Department department;
    private String name;
    private String email;
    private LocalDate date;
    private LocalTime time;

    @ManyToOne(cascade = PERSIST)
    private Doctor doctor;
}
