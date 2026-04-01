package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.request.CreateUserRequest;
import ivents.ivents_ui_support.dto.request.UpdateUserRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ivents/v1/users")
@Tag(name= "User Management")
public class UserController {
    private final UserService userService;

    @RequestMapping(
            path = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get users", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> getUsers(@RequestParam (name = "page") int page,
                                                   @RequestParam (name = "size") int size,
                                                   @RequestParam(name = "role_id", required = false) Long role_id,
                                                   @RequestParam(name = "is_active", required = false) Boolean is_active,
                                                   @RequestParam(name = "team_id", required = false) Long team_id){
        var response = userService.getUsers(page, size, role_id, is_active, team_id);

        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create users", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> createUser(@Validated(CreateUserRequest.ValidateRequest.class)
                                                         @RequestBody CreateUserRequest request){
        var response = userService.createUser(request);

        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/update",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update users", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> updateUser(@Validated(UpdateUserRequest.ValidateRequest.class)
                                                         @RequestBody UpdateUserRequest request){
        var response = userService.updateUser(request);

        return ManageEntityResponse.resolve(response);
    }
}
