package ro.fasttrackit.proiect.doctor.entity;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private DayOfWeek dayOfWeek;
    private String startDate;
    private String endDate;

}
