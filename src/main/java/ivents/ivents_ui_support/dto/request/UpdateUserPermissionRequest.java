package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserPermissionRequest {

    @NotNull(message = "Field `id` is required ", groups = { UpdateUserPermissionRequest.ValidateRequest.class })
    @JsonProperty("id")
    private Long id; 
    
    @NotNull(message = "Field `user_id` is required ", groups = { UpdateUserPermissionRequest.ValidateRequest.class })
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "Field `granted` is required ", groups = { UpdateUserPermissionRequest.ValidateRequest.class })
    @JsonProperty("granted")
    private Boolean granted;

    public interface ValidateRequest{

    }
}
