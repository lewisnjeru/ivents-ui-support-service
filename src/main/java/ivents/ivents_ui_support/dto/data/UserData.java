package ivents.ivents_ui_support.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class UserData {

    @JsonProperty ("ID")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("reset_password_token")
    private String resetPasswordToken;

    @JsonProperty("failed_login")
    private Integer failedLogin;

    @JsonProperty("locked_until")
    private Instant lockedUntil;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("modified_by")
    private String modifiedBy;

    @JsonProperty("created_on")
    private Instant createdOn;

    @JsonProperty("modified_on")
    private Instant modifiedOn;

    @JsonProperty("team_ids")
    private List<Long> teamIds;
}
