package ua.vholovetskyi.bookshop.notification.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Map;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-06-04
 */
@Data
@Builder
@Document(indexName = "notifications")
public class NotificationEntity {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String to;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Nested)
    private Map<String, Object> body;

    @Field(type = FieldType.Text)
    private String templateName;

    @Field(type = FieldType.Keyword)
    private NotificationStatus status;

    @Field(type = FieldType.Text)
    private String errorMessage;

    @Field(type = FieldType.Integer)
    private int numberOfAttempts;

    @Field(type = FieldType.Date)
    private Instant createdAt;

    @Field(type = FieldType.Date)
    private Instant updatedAt;

    public void updateFields(NotificationStatus newStatus, String errorMessage) {
        if (newStatus == null){
           throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = newStatus;
        this.errorMessage = errorMessage;
        ++numberOfAttempts;
        updatedAt = Instant.now();
    }
}
