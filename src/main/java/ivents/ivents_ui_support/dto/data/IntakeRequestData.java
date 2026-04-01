package ivents.ivents_ui_support.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntakeRequestData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("space_id")
    private Long spaceId;

    @JsonProperty("submitted_by_user_id")
    private Long submittedByUserId;

    @JsonProperty("requester_name")
    private String requesterName;

    @JsonProperty("requester_email")
    private String requesterEmail;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private String type;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("meeting_capture")
    private Boolean meetingCapture;

    @JsonProperty("created_task_id")
    private Long createdTaskId;

    @JsonProperty("automation_summary")
    private String automationSummary;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("modified_at")
    private Instant modifiedAt;
}