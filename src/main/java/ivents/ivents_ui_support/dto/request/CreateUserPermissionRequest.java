package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserPermissionRequest {

    @NotNull(message = "Field `user_id` is required ", groups = { CreateUserPermissionRequest.ValidateRequest.class })
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "Field `permissionId` is required ", groups = { CreateUserPermissionRequest.ValidateRequest.class })
    @JsonProperty("permission_id")
    private Long permissionId;

    @NotNull(message = "Field `granted` is required ", groups = { CreateUserPermissionRequest.ValidateRequest.class })
    @JsonProperty("granted")
    private Boolean granted;

    public interface ValidateRequest{

    }
}
