package ua.vholovetskyi.bookshop.notification.db;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.vholovetskyi.bookshop.notification.model.NotificationEntity;
import ua.vholovetskyi.bookshop.notification.model.NotificationStatus;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-04
 */
public interface NotificationRepository extends ElasticsearchRepository<NotificationEntity, String> {

    List<NotificationEntity> findAllByStatus(NotificationStatus status);
}
