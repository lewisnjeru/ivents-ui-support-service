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
public class CommentData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("space_id")
    private Long spaceId;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("modified_by")
    private String modifiedBy;

    @JsonProperty("created_on")
    private Instant createdOn;

    @JsonProperty("modified_on")
    private Instant modifiedOn;
}