package ua.vholovetskyi.bookshop.commons.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqPublisher implements NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.notification}")
    private String exchange;

    @Value("${rabbitmq.binding.notification}")
    private String routingKey;

    @Override
    public void publishNotification(NotificationMessageDto message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
