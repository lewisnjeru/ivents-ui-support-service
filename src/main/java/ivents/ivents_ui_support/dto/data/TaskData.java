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
public class TaskData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("space_id")
    private String spaceId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("due_date")
    private Instant dueDate;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @JsonProperty("reviewer_id")
    private Long reviewerId;

    @JsonProperty("created_on")
    private Instant createdOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("modified_by")
    private String modifiedBy;

    @JsonProperty("modified_on")
    private Instant modifiedOn;
}