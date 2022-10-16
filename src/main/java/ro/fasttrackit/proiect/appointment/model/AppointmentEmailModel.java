package ro.fasttrackit.proiect.appointment.model;

import lombok.Builder;

@Builder
public record AppointmentEmailModel(String name,
                                    String date,
                                    String time,
                                    String department,
                                    String doctor) {
}
