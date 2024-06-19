package ua.vholovetskyi.bookshop.notification.mapper;

import ua.vholovetskyi.bookshop.notification.model.NotificationEntity;
import ua.vholovetskyi.bookshop.notification.service.dto.NotificationDetailsDto;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-04
 */
public class NotificationDtoMapper {

    public static NotificationDetailsDto mapToNotificationDetailsDto(NotificationEntity email) {
        return NotificationDetailsDto.builder()
                .to(email.getTo())
                .subject(email.getSubject())
                .body(email.getBody())
                .templateName(email.getTemplateName())
                .build();
    }
}
