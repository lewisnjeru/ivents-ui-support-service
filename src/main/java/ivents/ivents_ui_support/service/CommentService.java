package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.CommentData;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateCommentRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.entity.Comment;
import ivents.ivents_ui_support.repository.CommentRepository;
import ivents.ivents_ui_support.specification.CommentSpecification;
import ivents.ivents_ui_support.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final AuthUtil authUtil;

    public EntityResponse fetchComments(int page, int size, Long spaceId, Long userId, Long parentId,
                                        Instant from, Instant to) {

        Pageable paging = PageRequest.of(page, size);

        // Dynamic, null-safe filtering using Specification
        Page<Comment> commentsPage = commentRepository.findAll(
                CommentSpecification.filterComments(spaceId, userId, parentId, from, to),
                paging
        );

        if (commentsPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No Records found.")
                    .build();
        }

        List<CommentData> commentsDataList = commentsPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(commentsPage.getNumber())
                .totalPages(commentsPage.getTotalPages())
                .totalItems(commentsPage.getTotalElements())
                .build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request processed successfully")
                .data(commentsDataList)
                .paginationResponse(paginationResponse)
                .build();
    }

    public EntityResponse createComment(CreateCommentRequest request) {
        Optional<UserData> userDataOpt = authUtil.getCurrentUser();
        String email = userDataOpt.map(UserData::getEmail).orElse("system");

        Comment comment = Comment.builder()
                .content(request.getContent())
                .userId(request.getUserId())
                .spaceId(request.getSpaceId())
                .parentId(request.getParentId())
                .createdBy(email)
                .modifiedBy(email)
                .createdOn(Instant.now())
                .modifiedOn(Instant.now())
                .build();

        commentRepository.save(comment);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request Processed Successfully")
                .build();
    }

    private CommentData mapToDto(Comment comment) {
        return CommentData.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUserId())
                .spaceId(comment.getSpaceId())
                .parentId(comment.getParentId())
                .createdBy(comment.getCreatedBy())
                .modifiedBy(comment.getModifiedBy())
                .createdOn(comment.getCreatedOn())
                .modifiedOn(comment.getModifiedOn())
                .build();
    }
}