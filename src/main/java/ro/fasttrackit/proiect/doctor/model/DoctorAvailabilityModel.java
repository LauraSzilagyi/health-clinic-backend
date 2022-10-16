package ro.fasttrackit.proiect.doctor.model;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorAvailabilityModel {

    private String date;
    private PeriodModel openInterval;
    private List<PeriodModel> bookedSchedules;
}
