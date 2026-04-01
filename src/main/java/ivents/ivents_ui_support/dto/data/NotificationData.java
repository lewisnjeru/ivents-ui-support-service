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
public class NotificationData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("href")
    private String href;

    @JsonProperty("dedupe_key")
    private String dedupeKey;

    @JsonProperty("read_at")
    private Instant readAt;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("created_on")
    private Instant createdOn;

    @JsonProperty("modified_on")
    private Instant modifiedOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("modified_by")
    private String modifiedBy;
}