package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateSpaceRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.service.SpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ivents/v1/spaces")
@Tag(name= "Space Management")
public class SpaceController {

    private final SpaceService spaceService;

    @RequestMapping(
            path ="/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Fetch Spaces", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> fetchSpaces(@RequestParam("page") int page,
                                                      @RequestParam("size") int size,
                                                      @RequestParam(name = "name", required = false) String name,
                                                      @RequestParam(name = "mode", required = false) String mode,
                                                      @RequestParam(name = "createdBy", required = false) String createdBy,
                                                      @RequestParam(name = "from", required = false) Instant from,
                                                      @RequestParam(name = "to", required = false) Instant to) {

        AtomicReference<EntityResponse> response = new AtomicReference<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsData authData) {
            response.set(spaceService.fetchSpaces(page, size, name, mode, createdBy, from, to));
        }

        return ManageEntityResponse.resolve(response.get());
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create Space", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> createSpace(@Validated(CreateSpaceRequest.ValidateRequest.class)
                                                      @Valid @RequestBody CreateSpaceRequest request) {
        var response = spaceService.createSpace(request);
        return ManageEntityResponse.resolve(response);
    }
}