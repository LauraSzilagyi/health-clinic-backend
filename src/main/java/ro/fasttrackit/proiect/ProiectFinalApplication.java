package ro.fasttrackit.proiect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ProiectFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProiectFinalApplication.class, args);
    }

}
