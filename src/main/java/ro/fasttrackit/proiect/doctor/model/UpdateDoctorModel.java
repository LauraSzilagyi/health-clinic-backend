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
public class UpdateDoctorModel {
    private String name;
    private String email;
    private String password;
    private String address;
    private String image;
    private String phone;
    private String description;
    private String birthdate;
    private Department department;
    private String specialist;
    private Integer experience;
    private List<DoctorAvailability> availabilities;
}
