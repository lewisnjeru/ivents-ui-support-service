package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateIntakeRequestRequest {

    @NotNull(message = "Field `space_id` is required ", groups = { ValidateRequest.class })
    @JsonProperty("space_id")
    private Long spaceId;

    @JsonProperty("submitted_by_user_id")
    private Long submittedByUserId;

    @NotNull(message = "Field `requester_name` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `requester_name` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("requester_name")
    private String requesterName;

    @NotNull(message = "Field `requester_email` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `requester_email` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("requester_email")
    private String requesterEmail;

    @NotNull(message = "Field `title` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `title` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("title")
    private String title;

    @NotNull(message = "Field `description` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `description` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("description")
    private String description;

    @NotNull(message = "Field `type` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `type` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("type")
    private String type;

    @NotNull(message = "Field `priority` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `priority` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("priority")
    private String priority;

    @JsonProperty("meeting_capture")
    private Boolean meetingCapture;

    @JsonProperty("created_task_id")
    private Long createdTaskId;

    @JsonProperty("automation_summary")
    private String automationSummary;

    public interface ValidateRequest {
    }
}