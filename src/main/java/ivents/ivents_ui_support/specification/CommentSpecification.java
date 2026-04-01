package ivents.ivents_ui_support.specification;

import ivents.ivents_ui_support.entity.Comment;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CommentSpecification {

    public static Specification<Comment> filterComments(
            Long spaceId,
            Long userId,
            Long parentId,
            Instant from,
            Instant to
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (spaceId != null) {
                predicates.add(cb.equal(root.get("spaceId"), spaceId));
            }

            if (userId != null) {
                predicates.add(cb.equal(root.get("userId"), userId));
            }

            if (parentId != null) {
                predicates.add(cb.equal(root.get("parentId"), parentId));
            }

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdOn"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdOn"), to));
            }

            query.orderBy(cb.desc(root.get("createdOn"))); // maintain ordering

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}