package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSpaceRequest {
    
    @NotNull(message = "Field `name` is required ", groups = { CreateSpaceRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `name` cannot be empty ", groups = { CreateSpaceRequest.ValidateRequest.class })
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Field `description` is required ", groups = { CreateSpaceRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `description` cannot be empty ", groups = { CreateSpaceRequest.ValidateRequest.class })
    @JsonProperty("description")
    private String description;
    
    @NotNull(message = "Field `mode` is required ", groups = { CreateSpaceRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `mode` cannot be empty ", groups = { CreateSpaceRequest.ValidateRequest.class })
    @JsonProperty("mode")
    private String mode;

    public interface ValidateRequest {
        
    }
}