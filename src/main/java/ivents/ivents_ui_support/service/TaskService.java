package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.TaskData;
import ivents.ivents_ui_support.dto.request.CreateTaskRequest;
import ivents.ivents_ui_support.dto.request.UpdateTaskRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.entity.Task;
import ivents.ivents_ui_support.repository.TaskRepository;
import ivents.ivents_ui_support.specification.TaskSpecification;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final AuthUtil authUtil;

    public EntityResponse fetchTasks(int page, int size, String spaceId, String title, String status,
                                     String type, String priority, String assigneeId,
                                     Instant from, Instant to) {

        Pageable paging = PageRequest.of(page, size);

        Page<Task> tasksPage = taskRepository.findAll(
                TaskSpecification.filterTasks(spaceId, title, status, type, priority, assigneeId, from, to),
                paging
        );

        if (tasksPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No Records found.")
                    .build();
        }

        List<TaskData> tasksDataList = tasksPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(tasksPage.getNumber())
                .totalPages(tasksPage.getTotalPages())
                .totalItems(tasksPage.getTotalElements())
                .build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request processed successfully")
                .data(tasksDataList)
                .paginationResponse(paginationResponse)
                .build();
    }

    public EntityResponse createTask(CreateTaskRequest request) {
        Optional<UserDetailsData> userDataOpt = authUtil.getCurrentUser();
        log.info("logged info {}", userDataOpt.get().getEmail());
        String email = userDataOpt.map(UserDetailsData::getEmail).orElse("system");

        Task task = Task.builder()
                .spaceId(request.getSpaceId())
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .type(request.getType())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .assigneeId(request.getAssigneeId())
                .reviewerId(request.getReviewerId())
                .createdOn(Instant.now())
                .createdBy(email)
                .build();

        taskRepository.save(task);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request Processed Successfully")
                .build();
    }

    public EntityResponse updateTask(UpdateTaskRequest request) {
        Optional<UserDetailsData> userDataOpt = authUtil.getCurrentUser();
        String email = userDataOpt.map(UserDetailsData::getEmail).orElse("system");

        Optional<Task> existingTaskOpt = taskRepository.findById(request.getId());

        if (existingTaskOpt.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Task not found.")
                    .build();
        }

        Task task = existingTaskOpt.get();

        task.setSpaceId(request.getSpaceId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setType(request.getType());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setAssigneeId(request.getAssigneeId());
        task.setReviewerId(request.getReviewerId());
        task.setModifiedOn(Instant.now());

        taskRepository.save(task);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request Processed Successfully")
                .build();
    }


    private TaskData mapToDto(Task task) {
        return TaskData.builder()
                .id(task.getId())
                .spaceId(task.getSpaceId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .type(task.getType())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assigneeId(task.getAssigneeId())
                .reviewerId(task.getReviewerId())
                .createdOn(task.getCreatedOn())
                .modifiedOn(task.getModifiedOn())
                .build();
    }
}