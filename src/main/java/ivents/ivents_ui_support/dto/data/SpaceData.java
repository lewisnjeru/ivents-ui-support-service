package ivents.ivents_ui_support.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("created_on")
    private Instant createdOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("modified_on")
    private Instant modifiedOn;

    @JsonProperty("modified_by")
    private String modifiedBy;
}