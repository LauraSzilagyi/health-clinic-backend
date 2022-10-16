package ro.fasttrackit.proiect.login.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record LoginResponseDTO(LoginResponseMessage message, Integer userId, String token) {
}
