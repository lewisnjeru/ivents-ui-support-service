package ivents.ivents_ui_support.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTeamRequest {

    @NotNull(message = "Field `name` is required ", groups = { CreateTeamRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `name` cannot be empty ", groups = { CreateTeamRequest.ValidateRequest.class })
    @JsonProperty("name")
    private String name;


    @NotNull(message = "Field `created_by` is required ", groups = { CreateTeamRequest.ValidateRequest.class })
    @NotEmpty(message = "Field `created_by` cannot be empty ", groups = { CreateTeamRequest.ValidateRequest.class })
    @JsonProperty("created_by")
    private String createdBy;


    public interface ValidateRequest{

    }
}
