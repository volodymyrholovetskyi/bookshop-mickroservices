package ua.vholovetskyi.bookshop.notification.service.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import ua.vholovetskyi.bookshop.commons.exception.TemplateException;
import ua.vholovetskyi.bookshop.notification.service.dto.NotificationDetailsDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements NotificationSender {

    private static final String FORMAT_PATH = "templates/emails/%s.html";

    private final JavaMailSender mailSender;

    @Value("${app.notification.sent.from}")
    private String fromSender;

    /**
     * Send email.
     *
     * @param notification data to send.
     */
    @Override
    public void sendNotification(NotificationDetailsDto notification) {
        var message = mailSender.createMimeMessage();
        try {
            var helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(notification.getTo());
            helper.setFrom(fromSender);
            helper.setReplyTo(fromSender);
            helper.setSubject(notification.getSubject());
            helper.setText(generateEmailBody(notification), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error in sendNotification method", e);
        }
    }

    /**
     * Generate body.
     *
     * @param notification data to create body.
     * @return html string.
     */
    private String generateEmailBody(NotificationDetailsDto notification) {
        if (Objects.isNull(notification.getBody())) {
            throw new IllegalArgumentException("Email body is null");
        }
        var templateName = notification.getTemplateName();

        var body = loadEmailTemplate(templateName);
        for (Map.Entry<String, Object> entry : notification.getBody().entrySet()) {
            body = body.replace("{{" + entry.getKey() + "}}", Objects.toString(entry.getValue()));
        }
        return body;
    }

    /**
     * Load template.
     *
     * @param templateName template name.
     * @return template.
     */
    private String loadEmailTemplate(String templateName) {
        var resource = new ClassPathResource(FORMAT_PATH.formatted(templateName));
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new TemplateException(templateName, e);
        }
    }
}
