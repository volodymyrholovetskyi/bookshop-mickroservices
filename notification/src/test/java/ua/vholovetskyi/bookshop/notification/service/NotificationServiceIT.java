package ua.vholovetskyi.bookshop.notification.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.mail.MailException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.vholovetskyi.bookshop.notification.model.NotificationEntity;
import ua.vholovetskyi.bookshop.notification.model.NotificationStatus;
import ua.vholovetskyi.bookshop.notification.service.dto.NotificationDetailsDto;
import ua.vholovetskyi.bookshop.notification.service.mail.NotificationSender;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NotificationServiceIT extends BaseContainerIT {

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private NotificationSender notificationSender;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    @BeforeAll
    static void setUp() {
        elasticContainer.start();
    }

    @BeforeEach
    void setUpContainerRunning() {
        assertTrue(elasticContainer.isRunning());
        recreateIndex();
    }

    @Test
    void shouldCreateNotificationAndUpdateStatusAsSent() {
        //given
        var notification = givenNotification();

        //when
        notificationService.saveNotification(notification);
        var emails = notificationService.findAllByStatus(NotificationStatus.SENT);

        //then
        assertEquals(emails.size(), 1);
        assertEquals(emails.get(0).getStatus(), NotificationStatus.SENT);
        assertNull(emails.get(0).getErrorMessage());
        verify(notificationSender, times(1)).sendNotification(any(NotificationDetailsDto.class));
    }

    @Test
    void shouldCreateNotificationAndUpdateStatusAsFailed() {
        //given
        var notification = givenNotification();

        //when
        doThrow(new MailException("Error occurred!") {}).when(notificationSender)
                .sendNotification(any());
        notificationService.saveNotification(notification);
        var emails = notificationService.findAllByStatus(NotificationStatus.FAILED);

        //then
        assertEquals(emails.size(), 1);
        assertEquals(emails.get(0).getStatus(), NotificationStatus.FAILED);
        assertNotNull(emails.get(0).getErrorMessage());
        verify(notificationSender, times(1)).sendNotification(any(NotificationDetailsDto.class));
    }

    private NotificationEntity givenNotification() {
        return NotificationEntity.builder()
                .to("shop@test.com")
                .subject("Test Subject")
                .body(Map.of("fullName", "First Customer"))
                .status(NotificationStatus.PENDING)
                .templateName("customer-template")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    private void recreateIndex() {
        if (elasticTemplate.indexOps(NotificationEntity.class).exists()) {
            elasticTemplate.indexOps(NotificationEntity.class).delete();
            elasticTemplate.indexOps(NotificationEntity.class).create();
        }
    }

    @AfterAll
    static void tearDown() {
        elasticContainer.stop();
    }
}