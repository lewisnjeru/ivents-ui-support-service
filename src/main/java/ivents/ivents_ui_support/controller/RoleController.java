package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.request.CreateRoleRequest;
import ivents.ivents_ui_support.dto.request.UpdateRoleRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/ivents/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles")
public class RoleController {

    private final RoleService roleService;

//    @RequestMapping(
//            path = "/get",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    @Operation(summary = "Fetch Roles", security = @SecurityRequirement(name = "BearerAuth"))
//    public ResponseEntity<EntityResponse> fetchRoles(@RequestParam("page") int page,
//                                                     @RequestParam("size") int size,
//                                                     @RequestParam(name = "name", required = false) String name,
//                                                     @RequestParam(name = "created_by", required = false) String createdBy,
//                                                     @RequestParam(name = "from", required = false) Instant from,
//                                                     @RequestParam(name = "to", required = false) Instant to) {
//
//        AtomicReference<EntityResponse> response = new AtomicReference<>();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof UserData authData) {
//            response.set(roleService.fetchRoles(page, size, name, createdBy, from, to));
//        }
//
//        return ManageEntityResponse.resolve(response.get());
//    }

    @RequestMapping(
            path = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Fetch Roles",
            security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> fetchRoles(@RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "created_by", required = false) String createdBy,
                                                     @RequestParam(name = "from", required = false) Instant from,
                                                     @RequestParam(name = "to", required = false) Instant to) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsData authData)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    EntityResponse.builder()
                            .status(false)
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("Unauthorized - Invalid authentication")
                            .build()
            );
        }

        EntityResponse response = roleService.fetchRoles(page, size, name, createdBy, from, to);
        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create Role", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> createRole(@Validated(CreateRoleRequest.ValidateRequest.class)
                                                     @Valid @RequestBody CreateRoleRequest request) {
        var response = roleService.createRole(request);
        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/update",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update Role", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> updateRole(@Validated(UpdateRoleRequest.ValidateRequest.class)
                                                     @Valid @RequestBody UpdateRoleRequest request) {
        var response = roleService.updateRole(request);
        return ManageEntityResponse.resolve(response);
    }
}