package ivents.ivents_ui_support.dto.response;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ManageEntityResponse {
    public static ResponseEntity<EntityResponse> resolve(EntityResponse entityResponse) {

        return ResponseEntity.status(entityResponse.getCode()).body(entityResponse);

    }
}
