package ivents.ivents_ui_support.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateCommentRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.ManageEntityResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.service.CommentService;
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
@RequestMapping("/ivents/v1/comments")
@RequiredArgsConstructor
@Tag(name="Comments")
public class CommentController {

    private final CommentService commentService;

    @RequestMapping(
            path = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Fetch Comments", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> fetchComments(@RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam(name = "space_id", required = false) Long spaceId,
                                                        @RequestParam(name = "user_id", required = false) Long userId,
                                                        @RequestParam(name = "parent_id", required = false) Long parentId,
                                                        @RequestParam(name = "from", required = false) Instant from,
                                                        @RequestParam(name = "to", required = false) Instant to) {

        AtomicReference<EntityResponse> response = new AtomicReference<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsData authData) {
            Long id = authData.getId();
            log.info("Fetching comments for id " + id);
            response.set(commentService.fetchComments(page, size, spaceId, userId, parentId, from, to));
        }


        return ManageEntityResponse.resolve(response.get());
    }

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create Comment", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<EntityResponse> createComment(@Validated(CreateCommentRequest.ValidateRequest.class)
                                                        @Valid @RequestBody CreateCommentRequest request) {
        var response = commentService.createComment(request);
        return ManageEntityResponse.resolve(response);
    }
}