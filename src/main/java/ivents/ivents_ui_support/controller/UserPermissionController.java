package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.request.CreateUserPermissionRequest;
import ivents.ivents_ui_support.dto.request.UpdateUserPermissionRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.service.UserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ivents/v1/user-permissions")
@RequiredArgsConstructor
@Tag(name = "User Permission")
public class UserPermissionController {

    private final UserPermissionService userPermissionService;

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResponse> create(@Validated(CreateUserPermissionRequest.ValidateRequest.class)
                                                     @RequestBody CreateUserPermissionRequest request) {

        EntityResponse response = userPermissionService.createUserPermission(request);
        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResponse> listUserPermissions(@RequestParam Long userId) {
        EntityResponse response = userPermissionService.getUserPermissions(userId);
        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/update",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EntityResponse> updateGranted( @Validated(UpdateUserPermissionRequest.ValidateRequest.class)
                                                             @RequestBody UpdateUserPermissionRequest request) {
       EntityResponse response = userPermissionService.updateGranted(request);
        return ManageEntityResponse.resolve(response);
    }
}