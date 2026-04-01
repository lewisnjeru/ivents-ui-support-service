package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotNull(message = "Field `id` is required ", groups = { UpdateUserRequest.ValidateRequest.class })
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "Field `username` is required ", groups = { UpdateUserRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `username` cannot be empty ", groups = { UpdateUserRequest.ValidateRequest.class })
    @JsonProperty("username")
    private String username;

    @NotNull(message = "Field `email` is required ", groups = { UpdateUserRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `email` cannot be empty ", groups = { UpdateUserRequest.ValidateRequest.class })
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Field `is_active` is required ", groups = { UpdateUserRequest.ValidateRequest.class })
    @JsonProperty("is_active")
    private Boolean isActive;

    @NotNull(message = "Field `role_id` is required ", groups = { UpdateUserRequest.ValidateRequest.class })
    @JsonProperty("role_id")
    private Long roleId;

    public interface ValidateRequest{

    }
}
