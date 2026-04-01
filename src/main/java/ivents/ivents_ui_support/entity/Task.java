package ivents.ivents_ui_support.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "space_id")
    private String spaceId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "priority")
    private String priority;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private Instant createdBy;

    @Column(name = "modified_on")
    private Instant modifiedOn;

    @Column(name = "modified_by")
    private Instant modifiedBy;
}