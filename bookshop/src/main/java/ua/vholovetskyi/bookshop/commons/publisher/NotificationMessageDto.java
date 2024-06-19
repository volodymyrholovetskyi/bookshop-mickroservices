package ua.vholovetskyi.bookshop.commons.publisher;

import lombok.*;

import java.util.Map;

@Data
@Builder
public class NotificationMessageDto {
    private String to;
    private String subject;
    private Map<String, Object> body;
    private String templateName;
}
