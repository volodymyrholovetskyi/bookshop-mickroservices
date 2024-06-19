package ua.vholovetskyi.bookshop.notification.mapper;

import ua.vholovetskyi.bookshop.notification.model.NotificationEntity;
import ua.vholovetskyi.bookshop.notification.model.NotificationStatus;
import ua.vholovetskyi.bookshop.notification.service.dto.NotificationMessageDto;

import java.time.Instant;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-04
 */
public class NotificationFactory {

    public static NotificationEntity createNewNotification(NotificationMessageDto notification) {
        return NotificationEntity.builder()
                .to(notification.getTo())
                .subject(notification.getSubject())
                .body(notification.getBody())
                .templateName(notification.getTemplateName())
                .status(NotificationStatus.PENDING)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
