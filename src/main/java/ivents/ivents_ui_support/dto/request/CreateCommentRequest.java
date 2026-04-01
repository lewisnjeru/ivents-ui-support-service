package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @NotNull(message = "Field `content` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `content` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("content")
    private String content;

    @NotNull(message = "Field `user_id` is required ", groups = { ValidateRequest.class })
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "Field `space_id` is required ", groups = { ValidateRequest.class })
    @JsonProperty("space_id")
    private Long spaceId;

    @JsonProperty("parent_id")
    private Long parentId;

    public interface ValidateRequest {
    }
}