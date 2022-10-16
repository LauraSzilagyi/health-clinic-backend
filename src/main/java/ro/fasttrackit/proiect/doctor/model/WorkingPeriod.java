package ro.fasttrackit.proiect.doctor.model;

import lombok.Builder;

import java.time.DayOfWeek;

@Builder
public record WorkingPeriod(DayOfWeek day, String startTime, String endTime) {
}
