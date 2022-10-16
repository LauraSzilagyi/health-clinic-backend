package ro.fasttrackit.proiect.login.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@ConfigurationProperties(prefix = "jwt")
public record JwtConfiguration(String secret, @DurationUnit(ChronoUnit.MINUTES) Duration validity) {
}
