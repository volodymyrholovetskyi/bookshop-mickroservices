package ua.vholovetskyi.bookshop.notification.service.mail;

import ua.vholovetskyi.bookshop.notification.service.dto.NotificationDetailsDto;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-04
 */
public interface NotificationSender {

    /**
     * Send notification.
     *
     * @param notification data to send.
     */
    void sendNotification(NotificationDetailsDto notification);
}
