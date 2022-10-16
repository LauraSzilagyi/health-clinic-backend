package ro.fasttrackit.proiect.email.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ro.fasttrackit.proiect.email.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentEmailSender implements EmailService {

    private final JavaMailSender emailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;

    @Override
    public void sendEmail(String to, Map<String, Object> model) {

        try {
            MimeMessage message = emailSender.createMimeMessage();

            Template template = freemarkerConfigurer.getConfiguration().getTemplate("appointment-email-template.ftl");
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(to);
            helper.setText(htmlBody, true);
            helper.setSubject("Appointment registered");
            emailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            log.error(e.getMessage());
        }
    }
}

