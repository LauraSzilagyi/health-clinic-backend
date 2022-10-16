package ro.fasttrackit.proiect.doctor.model;

import lombok.Builder;
import ro.fasttrackit.proiect.doctor.enums.Department;

import java.util.List;

@Builder
public record DoctorModel(Integer id,
                          String name,
                          String specialist,
                          String description,
                          String email,
                          String password,
                          String birthdate,
                          String address,
                          String phone,
                          Department department,
                          int experience,
                          List<WorkingPeriod> availabilities,
                          String image

) {
}
