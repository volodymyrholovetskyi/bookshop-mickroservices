package ua.vholovetskyi.bookshop.notification.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-03
 */
@Data
@Builder
public class NotificationMessageDto {

    private String to;
    private String subject;
    private Map<String, Object> body;
    private String templateName;
}
