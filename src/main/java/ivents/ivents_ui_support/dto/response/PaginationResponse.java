package ivents.ivents_ui_support.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse implements Serializable {
    @Builder.Default
    @JsonProperty("current_page")
    private Integer currentPage = 0;

    @Builder.Default
    @JsonProperty("total_items")
    private Long totalItems = 0L ;

    @Builder.Default
    @JsonProperty("total_pages")
    private Integer totalPages = 0;
}
