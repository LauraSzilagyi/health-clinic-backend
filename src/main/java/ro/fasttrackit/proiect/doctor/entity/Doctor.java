package ro.fasttrackit.proiect.doctor.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ro.fasttrackit.proiect.appointment.entity.Appointment;
import ro.fasttrackit.proiect.doctor.enums.Department;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Person {

    private LocalDate birthdate;
    private String address;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;
    private String phone;
    private String description;
    private Department department;
    private String specialist;
    private int experience;

    @OneToMany(mappedBy = "doctor", cascade = ALL)
    private List<Appointment> appointments;

    @OneToMany(cascade = ALL)
    private List<DoctorAvailability> availabilities;
}
