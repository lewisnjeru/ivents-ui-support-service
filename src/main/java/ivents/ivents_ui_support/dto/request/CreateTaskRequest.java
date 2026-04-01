package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateTaskRequest {

    @NotNull(message = "Field `space_id` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `space_id` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("space_id")
    private String spaceId;

    @NotNull(message = "Field `title` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `title` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("title")
    private String title;

    @NotNull(message = "Field `description` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `description` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("description")
    private String description;

    @NotNull(message = "Field `status` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `status` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("status")
    private String status;

    @NotNull(message = "Field `type` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `type` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("type")
    private String type;

    @NotNull(message = "Field `priority` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `priority` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("priority")
    private String priority;

    @JsonProperty("due_date")
    private Instant dueDate;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @JsonProperty("reviewer_id")
    private Long reviewerId;

    public interface ValidateRequest {
    }
}