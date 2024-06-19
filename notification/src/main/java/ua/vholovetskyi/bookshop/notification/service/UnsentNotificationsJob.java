package ua.vholovetskyi.bookshop.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.notification.model.NotificationStatus;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UnsentNotificationsJob {

    private final NotificationService messageService;

    /**
     * Task scheduler.
     * Search and tries to resend notifications.
     */
    @Transactional
    @Scheduled(cron = "${app.notification.failed-cron}")
    public void run() {
        try {
            log.info("Starting Scheduled Task...");
            var notifications = messageService.findAllByStatus(NotificationStatus.FAILED);
            log.info("Found failed notifications to resend: {}", notifications.size());
            notifications.forEach(messageService::sendNotification);
            log.info("Scheduled Task completed successfully!");
        } catch (Exception e) {
            log.error("Error executing scheduled task: {}", e.getMessage());
        }
    }
}
