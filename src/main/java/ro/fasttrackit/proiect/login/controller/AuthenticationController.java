package ro.fasttrackit.proiect.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.proiect.login.jwt.dto.LoginResponseDTO;
import ro.fasttrackit.proiect.login.jwt.dto.RequestDTO;
import ro.fasttrackit.proiect.login.service.AuthenticationService;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> generateAuthenticationToken(@RequestParam Boolean isAdmin,
                                                                        @RequestParam String email,
                                                                        @RequestParam String password) {
        RequestDTO payload = new RequestDTO(email, password);
        var response = authService.generateToken(payload, isAdmin);
        return ResponseEntity.ok(response);
    }
}
