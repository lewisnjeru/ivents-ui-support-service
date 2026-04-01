package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.IntakeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface IntakeRequestRepository extends JpaRepository<IntakeRequest, Long> {

    @Query("SELECT ir FROM IntakeRequest ir WHERE " +
            "(:spaceId IS NULL OR ir.spaceId = :spaceId) AND " +
            "(:submittedByUserId IS NULL OR ir.submittedByUserId = :submittedByUserId) AND " +
            "(:type IS NULL OR ir.type = :type) AND " +
            "(:priority IS NULL OR ir.priority = :priority) AND " +
            "(:meetingCapture IS NULL OR ir.meetingCapture = :meetingCapture) AND " +
            "(:requesterEmail IS NULL OR LOWER(ir.requesterEmail) LIKE LOWER(CONCAT('%', :requesterEmail, '%'))) AND " +
            "(:isDeleted IS NULL OR ir.isDeleted = :isDeleted) AND " +
            "(:from IS NULL OR ir.createdOn >= :from) AND " +
            "(:to IS NULL OR ir.createdOn <= :to) " +
            "ORDER BY ir.createdOn DESC")
    Page<IntakeRequest> fetchIntakeRequests(Pageable pageable,
                                            @Param("spaceId") Long spaceId,
                                            @Param("submittedByUserId") Long submittedByUserId,
                                            @Param("type") String type,
                                            @Param("priority") String priority,
                                            @Param("meetingCapture") Boolean meetingCapture,
                                            @Param("requesterEmail") String requesterEmail,
                                            @Param("isDeleted") Boolean isDeleted,
                                            @Param("from") Instant from,
                                            @Param("to") Instant to);
}