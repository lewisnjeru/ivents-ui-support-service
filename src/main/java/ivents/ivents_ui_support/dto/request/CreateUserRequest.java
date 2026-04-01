package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotNull(message = "Field `username` is required ", groups = { CreateUserRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `username` cannot be empty ", groups = { CreateUserRequest.ValidateRequest.class })
    @JsonProperty("username")
    private String username;

    @NotNull(message = "Field `email` is required ", groups = { CreateUserRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `email` cannot be empty ", groups = { CreateUserRequest.ValidateRequest.class })
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Field `password` is required ", groups = { CreateUserRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `password` cannot be empty ", groups = { CreateUserRequest.ValidateRequest.class })
    @JsonProperty("password")
    private String password;

    @NotNull(message = "Field `role_id` is required ", groups = { CreateUserRequest.ValidateRequest.class })
    @JsonProperty("role_id")
    private Long roleId;

    public interface ValidateRequest{

    }
}