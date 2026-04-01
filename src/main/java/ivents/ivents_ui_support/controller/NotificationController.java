package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/ivents/v1/notifications")
@RequiredArgsConstructor
@Tag(name ="Notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Fetch Notifications",
            security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> fetchNotifications(@RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam(name = "user_id", required = false) Long userId,
                                                             @RequestParam(name = "type", required = false) String type,
                                                             @RequestParam(name = "read", required = false) Boolean read,
                                                             @RequestParam(name = "is_deleted", required = false) Boolean isDeleted,
                                                             @RequestParam(name = "from", required = false) Instant from,
                                                             @RequestParam(name = "to", required = false) Instant to) {

        AtomicReference<EntityResponse> response = new AtomicReference<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsData authData) {
            response.set(notificationService.fetchNotifications(page, size, userId, type, read, isDeleted, from, to));
        }

        return ManageEntityResponse.resolve(response.get());
    }
}