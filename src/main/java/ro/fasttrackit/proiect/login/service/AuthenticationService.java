package ro.fasttrackit.proiect.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.fasttrackit.proiect.doctor.entity.Person;
import ro.fasttrackit.proiect.login.jwt.dto.LoginResponseDTO;
import ro.fasttrackit.proiect.login.jwt.dto.RequestDTO;
import ro.fasttrackit.proiect.login.jwt.util.JwtTokenUtil;
import ro.fasttrackit.proiect.doctor.repository.DoctorRepository;

import java.util.Optional;

import static ro.fasttrackit.proiect.login.jwt.dto.LoginResponseMessage.NOT_FOUND;
import static ro.fasttrackit.proiect.login.jwt.dto.LoginResponseMessage.SUCCESS;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final DoctorRepository doctorRepository;

    public LoginResponseDTO generateToken(RequestDTO request, Boolean isAdmin) {
        Optional<Person> person = doctorRepository.findByEmailAndIsAdmin(request.email(), isAdmin);

        if (person.isPresent() && person.get().getPassword().equals(request.password())) {
            UserDetails user = jwtTokenUtil.buildUser(request.email());
            String token = jwtTokenUtil.generateToken(user);
            return new LoginResponseDTO(SUCCESS, person.get().getId(), token);
        } else {
            return new LoginResponseDTO(NOT_FOUND, null, null);
        }
    }
}
