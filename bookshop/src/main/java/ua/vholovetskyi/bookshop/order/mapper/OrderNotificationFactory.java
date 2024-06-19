package ua.vholovetskyi.bookshop.order.mapper;

import ua.vholovetskyi.bookshop.commons.publisher.NotificationMessageDto;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;

import java.util.Map;

public class OrderNotificationFactory {

    private static final String SUBJECT = "Create Order";
    private static final String TEMPLATE_NAME = "order-template";

    public static NotificationMessageDto createNotification(CustomerEntity customer, OrderEntity order) {
        return NotificationMessageDto.builder()
                .to(customer.getEmail())
                .subject(SUBJECT)
                .body(Map.of(
                        "orderId", order.getId(),
                        "status", order.getStatus(),
                        "fullName", customer.getFullName()))
                .templateName(TEMPLATE_NAME)
                .build();
    }
}
