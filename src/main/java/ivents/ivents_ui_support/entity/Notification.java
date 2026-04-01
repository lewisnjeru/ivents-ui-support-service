package ivents.ivents_ui_support.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "href")
    private String href;

    @Column(name = "dedupe_key")
    private String dedupeKey;

    @Column(name = "read_at")
    private Instant readAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;

    @Column(name = "created_by")
    private String createdBy ;

    @Column(name = "modified_by")
    private String modifiedBy ;
}