package ivents.ivents_ui_support.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaDataResponse implements Serializable {
    @Builder.Default
    @JsonProperty("timestamp")
    private Instant timestamp = Instant.now();

    @Builder.Default
    @JsonProperty("tat")
    private String tat = "0ms";
}
