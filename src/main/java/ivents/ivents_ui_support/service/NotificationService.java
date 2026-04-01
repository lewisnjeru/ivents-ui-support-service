package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.NotificationData;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.entity.Notification;
import ivents.ivents_ui_support.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public EntityResponse fetchNotifications(int page, int size, Long userId, String type,
                                             Boolean read, Boolean isDeleted,
                                             Instant from, Instant to) {
        Pageable paging = PageRequest.of(page, size);

        Page<Notification> notificationsPage = notificationRepository.fetchNotifications(
                paging, userId, type, read, isDeleted, from, to);

        if (notificationsPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No Records found.")
                    .build();
        }

        List<NotificationData> notificationsDataList = notificationsPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(notificationsPage.getNumber())
                .totalPages(notificationsPage.getTotalPages())
                .totalItems(notificationsPage.getTotalElements())
                .build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request processed successfully")
                .data(notificationsDataList)
                .paginationResponse(paginationResponse)
                .build();
    }

    private NotificationData mapToDto(Notification notification) {
        return NotificationData.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .title(notification.getTitle())
                .body(notification.getBody())
                .href(notification.getHref())
                .dedupeKey(notification.getDedupeKey())
                .readAt(notification.getReadAt())
                .isDeleted(notification.getIsDeleted())
                .createdOn(notification.getCreatedOn())
                .modifiedOn(notification.getModifiedOn())
                .createdBy(notification.getCreatedBy())
                .modifiedBy(notification.getModifiedBy())
                .build();
    }
}