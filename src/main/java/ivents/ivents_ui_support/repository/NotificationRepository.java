package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE " +
            "(:userId IS NULL OR n.userId = :userId) AND " +
            "(:type IS NULL OR n.type = :type) AND " +
            "(:read IS NULL OR (:read = TRUE AND n.readAt IS NOT NULL) OR (:read = FALSE AND n.readAt IS NULL)) AND " +
            "(:isDeleted IS NULL OR n.isDeleted = :isDeleted) AND " +
            "(:from IS NULL OR n.createdOn >= :from) AND " +
            "(:to IS NULL OR n.createdOn <= :to) " +
            "ORDER BY n.createdOn DESC")
    Page<Notification> fetchNotifications(Pageable pageable,
                                          @Param("userId") Long userId,
                                          @Param("type") String type,
                                          @Param("read") Boolean read,
                                          @Param("isDeleted") Boolean isDeleted,
                                          @Param("from") Instant from,
                                          @Param("to") Instant to);
}