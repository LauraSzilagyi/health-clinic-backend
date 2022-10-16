package ro.fasttrackit.proiect.doctor.model;

import lombok.*;
import ro.fasttrackit.proiect.doctor.entity.DoctorAvailability;
import ro.fasttrackit.proiect.doctor.enums.Department;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewDoctorModel {
    private String email;
    private String password;
    private String name;
    private String birthdate;
    private String address;
    private String image;
    private String phone;
    private String description;
    private Department department;
    private String specialist;
    private int experience;
    private List<DoctorAvailability> availabilities;
}
