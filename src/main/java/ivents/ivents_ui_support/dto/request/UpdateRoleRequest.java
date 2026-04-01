package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {

    @NotNull(message = "Field `id` is required ", groups = { ValidateRequest.class })
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "Field `name` is required ", groups = { ValidateRequest.class })
    @NotEmpty(message = "Field `name` cannot be empty ", groups = { ValidateRequest.class })
    @JsonProperty("name")
    private String name;

    public interface ValidateRequest {
    }
}