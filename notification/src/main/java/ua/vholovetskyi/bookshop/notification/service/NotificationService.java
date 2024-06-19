package ua.vholovetskyi.bookshop.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.notification.service.mail.NotificationSender;
import ua.vholovetskyi.bookshop.notification.db.NotificationRepository;
import ua.vholovetskyi.bookshop.notification.model.NotificationEntity;
import ua.vholovetskyi.bookshop.notification.model.NotificationStatus;

import java.util.List;

import static ua.vholovetskyi.bookshop.notification.mapper.NotificationDtoMapper.mapToNotificationDetailsDto;
import static ua.vholovetskyi.bookshop.commons.utils.MessageFormatters.EMPTY_MSG;
import static ua.vholovetskyi.bookshop.commons.utils.MessageFormatters.formatMessage;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-03
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;

    /**
     * Find all notifications by status.
     *
     * @param status find by criteria.
     * @return List<NotificationEntity> or an emptyList if nothing is found.
     */
    public List<NotificationEntity> findAllByStatus(NotificationStatus status) {
        return notificationRepository.findAllByStatus(status);
    }

    /**
     * Save notification in DB.
     *
     * @param notification data to save in DB.
     */
    @Transactional
    public void saveNotification(NotificationEntity notification) {
        var savedEmail = notificationRepository.save(notification);
        sendNotification(savedEmail);
    }

    /**
     * Send email and changes status.
     * If the mail is sent successfully, the status updates to SENT
     * otherwise status updates to FAILED.
     *
     * @param notification data to send.
     */
    public void sendNotification(NotificationEntity notification) {
        try {
            log.info("Start sending notification...");
            notificationSender.sendNotification(mapToNotificationDetailsDto(notification));
            updateFields(notification, NotificationStatus.SENT, EMPTY_MSG);
            log.info("Notification sent successfully!");
        } catch (MailException err) {
            updateFields(notification, NotificationStatus.FAILED, formatMessage(err));
            log.error("Error sending notification. {}", err.getMessage());
        } catch (Exception err) {
            log.error("Error in sendNotification() method. {}", err.getMessage());
        }
    }

    private void updateFields(NotificationEntity notification, NotificationStatus status, String errorMsg) {
        notification.updateFields(status, errorMsg);
        notificationRepository.save(notification);
    }
}
