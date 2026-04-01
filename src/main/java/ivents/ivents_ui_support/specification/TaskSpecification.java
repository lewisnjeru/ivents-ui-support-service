package ivents.ivents_ui_support.specification;

import ivents.ivents_ui_support.entity.Task;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> filterTasks(
            String spaceId,
            String title,
            String status,
            String type,
            String priority,
            String assigneeId,
            Instant from,
            Instant to
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (spaceId != null && !spaceId.isEmpty()) {
                predicates.add(cb.equal(root.get("spaceId"), spaceId));
            }

            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                ));
            }

            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (type != null && !type.isEmpty()) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            if (priority != null && !priority.isEmpty()) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }

            if (assigneeId != null && !assigneeId.isEmpty()) {
                predicates.add(cb.equal(root.get("assigneeId"), assigneeId));
            }

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdOn"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdOn"), to));
            }

            query.orderBy(cb.desc(root.get("createdOn")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}