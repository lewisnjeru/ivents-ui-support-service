package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateTaskRequest;
import ivents.ivents_ui_support.dto.request.UpdateTaskRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/ivents/v1/tasks")
@RequiredArgsConstructor
@Tag(name ="Task Management")
public class TaskController {

    private final TaskService taskService;

    @RequestMapping(
            path = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Fetch Tasks",
            security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> fetchTasks(@RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestParam(name = "spaceId", required = false) String spaceId,
                                                     @RequestParam(name = "title", required = false) String title,
                                                     @RequestParam(name = "status", required = false) String status,
                                                     @RequestParam(name = "type", required = false) String type,
                                                     @RequestParam(name = "priority", required = false) String priority,
                                                     @RequestParam(name = "assigneeId", required = false) String assigneeId,
                                                     @RequestParam(name = "from", required = false) Instant from,
                                                     @RequestParam(name = "to", required = false) Instant to) {

        AtomicReference<EntityResponse> response = new AtomicReference<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsData authData) {
            response.set(taskService.fetchTasks(page, size, spaceId, title, status, type, priority, assigneeId, from, to));
        }

        return ManageEntityResponse.resolve(response.get());
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create Task", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> createTask(@Validated(CreateTaskRequest.ValidateRequest.class)
                                                     @Valid @RequestBody CreateTaskRequest request) {
        var response = taskService.createTask(request);
        return ManageEntityResponse.resolve(response);
    }

    @RequestMapping(
            path = "/update",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update Task", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> updateTask(@Validated(UpdateTaskRequest.ValidateRequest.class)
                                                     @Valid @RequestBody UpdateTaskRequest request) {
        var response = taskService.updateTask(request);
        return ManageEntityResponse.resolve(response);
    }
}