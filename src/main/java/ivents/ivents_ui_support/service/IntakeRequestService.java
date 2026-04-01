package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.IntakeRequestData;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateIntakeRequestRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.entity.IntakeRequest;
import ivents.ivents_ui_support.repository.IntakeRequestRepository;
import ivents.ivents_ui_support.util.AuthUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class IntakeRequestService {

    private IntakeRequestRepository intakeRequestRepository;

    private final AuthUtil authUtil;

    public EntityResponse fetchIntakeRequests(int page, int size, Long spaceId, Long submittedByUserId,
                                              String type, String priority, Boolean meetingCapture,
                                              String requesterEmail, Boolean isDeleted,
                                              Instant from, Instant to) {
        Pageable paging = PageRequest.of(page, size);

        Page<IntakeRequest> intakeRequestsPage = intakeRequestRepository.fetchIntakeRequests(
                paging, spaceId, submittedByUserId, type, priority, meetingCapture, requesterEmail, isDeleted, from, to);

        if (intakeRequestsPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No Records found.")
                    .build();
        }

        List<IntakeRequestData> intakeRequestsDataList = intakeRequestsPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(intakeRequestsPage.getNumber())
                .totalPages(intakeRequestsPage.getTotalPages())
                .totalItems(intakeRequestsPage.getTotalElements())
                .build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request processed successfully")
                .data(intakeRequestsDataList)
                .paginationResponse(paginationResponse)
                .build();
    }

    public EntityResponse createIntakeRequest(CreateIntakeRequestRequest request) {
        Optional<UserData> userDataOpt = authUtil.getCurrentUser();
        Long userId = userDataOpt.map(UserData::getId).orElse(null);

        IntakeRequest intakeRequest = IntakeRequest.builder()
                .spaceId(request.getSpaceId())
                .submittedByUserId(userId)
                .requesterName(request.getRequesterName())
                .requesterEmail(request.getRequesterEmail())
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .priority(request.getPriority())
                .meetingCapture(request.getMeetingCapture() != null ? request.getMeetingCapture() : false)
                .createdTaskId(request.getCreatedTaskId())
                .automationSummary(request.getAutomationSummary() != null ? request.getAutomationSummary() : "")
                .isDeleted(false)
                .createdOn(Instant.now())
                .modifiedOn(Instant.now())
                .build();

        intakeRequestRepository.save(intakeRequest);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request Processed Successfully")
                .build();
    }

    private IntakeRequestData mapToDto(IntakeRequest intakeRequest) {
        return IntakeRequestData.builder()
                .id(intakeRequest.getId())
                .spaceId(intakeRequest.getSpaceId())
                .submittedByUserId(intakeRequest.getSubmittedByUserId())
                .requesterName(intakeRequest.getRequesterName())
                .requesterEmail(intakeRequest.getRequesterEmail())
                .title(intakeRequest.getTitle())
                .description(intakeRequest.getDescription())
                .type(intakeRequest.getType())
                .priority(intakeRequest.getPriority())
                .meetingCapture(intakeRequest.getMeetingCapture())
                .createdTaskId(intakeRequest.getCreatedTaskId())
                .automationSummary(intakeRequest.getAutomationSummary())
                .isDeleted(intakeRequest.getIsDeleted())
                .createdAt(intakeRequest.getCreatedOn())
                .modifiedAt(intakeRequest.getModifiedBy())
                .build();
    }
}