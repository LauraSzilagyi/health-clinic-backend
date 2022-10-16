package ro.fasttrackit.proiect.doctor.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodModel {
    String start;
    String end;
}