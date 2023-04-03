package id.co.mii.serverapp.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender ms;
    private SpringTemplateEngine template;

    @Async
    public void send(EmailRequest eR) {

        try {
            MimeMessage msg = ms.createMimeMessage();
            MimeMessageHelper hlp = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "utf-8");

            Context ctx = new Context();
            ctx.setVariables(eR.getProps());

            String html = template.process("verification-email", ctx);

            hlp.setTo(eR.getTo());
            hlp.setText(html, true);
            hlp.setSubject("Confirm your email");

            ms.send(msg);
            System.out.println("\nEmail success to send");

        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send mail");
        }
    }

}
