package ivents.ivents_ui_support.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class TeamData {

    @JsonProperty("name")
    private String name;

    @JsonProperty("member_ids")
    private List<Long> memberIds;

    @JsonProperty("created_on")
    private String createdOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("modified_on")
    private String modifiedOn;

    @JsonProperty ("modified_by")
    private String modifiedBy;

}
