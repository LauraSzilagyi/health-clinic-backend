package ro.fasttrackit.proiect.appointment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AppointmentResponseModel(@JsonProperty(value = "status") String status) {
}
