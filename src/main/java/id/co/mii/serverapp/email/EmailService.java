package id.co.mii.serverapp.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender ms;

    @Override
    @Async
    public void send(String to, String email) {

        try {
            MimeMessage msg = ms.createMimeMessage();
            MimeMessageHelper hlp = new MimeMessageHelper(msg, "utf-8");

            hlp.setText(email);
            hlp.setTo(to);
            hlp.setSubject("Confirm your email");
            hlp.setFrom("hello@amigoscode.com");

        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send mail");
        }
    }

}
