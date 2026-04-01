package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateIntakeRequestRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.service.IntakeRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/ivents/v1/intake-requests")
@RequiredArgsConstructor
@Tag(name ="Intake Requests")
public class IntakeRequestController {

    private final IntakeRequestService intakeRequestService;

    @RequestMapping(
            path = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Fetch Intake Requests", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> fetchIntakeRequests(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam(name = "space_id", required = false) Long spaceId,
                                                              @RequestParam(name = "submitted_by_user_id", required = false) Long submittedByUserId,
                                                              @RequestParam(name = "type", required = false) String type,
                                                              @RequestParam(name = "priority", required = false) String priority,
                                                              @RequestParam(name = "meeting_capture", required = false) Boolean meetingCapture,
                                                              @RequestParam(name = "requester_email", required = false) String requesterEmail,
                                                              @RequestParam(name = "is_deleted", required = false) Boolean isDeleted,
                                                              @RequestParam(name = "from", required = false) Instant from,
                                                              @RequestParam(name = "to", required = false) Instant to) {

        AtomicReference<EntityResponse> response = new AtomicReference<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserData authData) {
            response.set(intakeRequestService.fetchIntakeRequests(page, size, spaceId, submittedByUserId, type, priority, meetingCapture, requesterEmail, isDeleted, from, to));
        }

        return ManageEntityResponse.resolve(response.get());
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create Intake Request", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> createIntakeRequest(@Validated(CreateIntakeRequestRequest.ValidateRequest.class)
                                                              @Valid @RequestBody CreateIntakeRequestRequest request) {
        var response = intakeRequestService.createIntakeRequest(request);
        return ManageEntityResponse.resolve(response);
    }
}