package ivents.ivents_ui_support.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityResponse {
    @Builder.Default
    @JsonProperty("status")
    private Boolean status = false;

    @Builder.Default
    @JsonProperty("code")
    private Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @Builder.Default
    @JsonProperty("message")
    private String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

    @Builder.Default
    @JsonProperty("data")
    private Object data = new HashMap<>();

    @Builder.Default
    @JsonProperty("pagination")
    private PaginationResponse paginationResponse = new PaginationResponse();

    @Builder.Default
    @JsonProperty("meta")
    private MetaDataResponse metaDataResponse = new MetaDataResponse();

    @Builder.Default
    @JsonProperty("errors")
    private List<String> errors = new ArrayList<>();
}
