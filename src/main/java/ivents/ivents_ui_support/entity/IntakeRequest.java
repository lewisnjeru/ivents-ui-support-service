package ivents.ivents_ui_support.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "intake_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntakeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "space_id")
    private Long spaceId;

    @Column(name = "submitted_by_user_id")
    private Long submittedByUserId;

    @Column(name = "requester_name")
    private String requesterName;

    @Column(name = "requester_email")
    private String requesterEmail;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String priority;

    @Column(name = "meeting_capture")
    private Boolean meetingCapture;

    @Column(name = "created_task_id")
    private Long createdTaskId;

    @Column(name = "automation_summary")
    private String automationSummary;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private Instant createdBy;

    @Column(name = "modified_on")
    private Instant modifiedOn;

    @Column(name = "modified_by")
    private Instant modifiedBy;
}