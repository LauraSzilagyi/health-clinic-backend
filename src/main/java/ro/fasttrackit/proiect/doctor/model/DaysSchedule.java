package ro.fasttrackit.proiect.doctor.model;

import lombok.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DaysSchedule {
    private DayOfWeek day;
    private List<ScheduleModel> schedules = new ArrayList<>();

    public void addSchedule(ScheduleModel schedule) {
        schedules.add(schedule);
    }
}
