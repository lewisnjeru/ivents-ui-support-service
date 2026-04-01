package ivents.ivents_ui_support.dto.role_permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDetailsData {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("permissions")
    private List<String> permissions;
}