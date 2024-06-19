package ua.vholovetskyi.bookshop.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ua.vholovetskyi.bookshop.notification.service.dto.NotificationMessageDto;

import static ua.vholovetskyi.bookshop.notification.mapper.NotificationFactory.createNewNotification;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-02
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMqListener {

    private final NotificationService notificationService;

    /**
     * Receives data from the notification-queue.
     *
     * @param notificationDto received data.
     */
    @RabbitListener(queues = "${rabbitmq.queue.notification}", concurrency = "5")
    public void handleEmailMessage(final NotificationMessageDto notificationDto) {
        notificationService.saveNotification(createNewNotification(notificationDto));
    }
}
