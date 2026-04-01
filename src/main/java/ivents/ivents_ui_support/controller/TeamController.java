package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.request.CreateTeamRequest;
import ivents.ivents_ui_support.dto.request.CreateUserRequest;
import ivents.ivents_ui_support.dto.request.UpdateUserRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ivents/v1/teams")
@Tag(name= "Team Management")
public class TeamController {
    private final TeamService teamService;

    @RequestMapping(
            path = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get Teams", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> getTeams(@RequestParam(name = "page") int page,
                                                   @RequestParam (name = "size") int size,
                                                   @RequestParam(name = "team_id", required = false) Long team_id){
        var response = teamService.getTeams(page, size, team_id);

        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create Teams", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> createTeam(@Validated(CreateTeamRequest.ValidateRequest.class)
                                                     @RequestBody CreateTeamRequest request){
        var response = teamService.createTeam(request);

        return ManageEntityResponse.resolve(response);
    }
//
//    @RequestMapping(
//            path = "/update",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    @Operation(summary = "Update Teams", security = @SecurityRequirement(name = "BearerAuth"))
//    public ResponseEntity<EntityResponse> updateTeam(@Validated(UpdateTeamRequest.ValidateRequest.class)
//                                                     @RequestBody UpdateTeamRequest request){
//        var response = teamService.updateTeam(request);
//
//        return ManageEntityResponse.resolve(response);
//    }
}
