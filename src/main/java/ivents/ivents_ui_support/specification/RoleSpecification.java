package ivents.ivents_ui_support.specification;

import ivents.ivents_ui_support.entity.Role;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RoleSpecification {

    public static Specification<Role> filterRoles(
            String name,
            String createdBy,
            Instant from,
            Instant to
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (createdBy != null && !createdBy.isEmpty()) {
                predicates.add(cb.equal(root.get("createdBy"), createdBy));
            }

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdOn"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdOn"), to));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}