package ua.vholovetskyi.bookshop.customer.mapper;

import ua.vholovetskyi.bookshop.commons.publisher.NotificationMessageDto;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;

import java.util.Map;

public class CustomerNotificationFactory {

    private static final String SUBJECT = "Registration Customer";
    private static final String TEMPLATE_NAME = "customer-template";

    public static NotificationMessageDto createNotification(CustomerEntity customer) {
        return NotificationMessageDto.builder()
                .to(customer.getEmail())
                .subject(SUBJECT)
                .body(Map.of("fullName", customer.getFullName()))
                .templateName(TEMPLATE_NAME)
                .build();
    }
}
