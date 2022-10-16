package ro.fasttrackit.proiect.email.service;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, Map<String, Object> model);
}
