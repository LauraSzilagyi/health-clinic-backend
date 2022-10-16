package ro.fasttrackit.proiect.doctor.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleModel {
    private String name;
    private String dataStart;
    private String dataEnd;
}
