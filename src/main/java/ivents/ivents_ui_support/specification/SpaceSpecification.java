package ivents.ivents_ui_support.specification;

import ivents.ivents_ui_support.entity.Space;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SpaceSpecification {

    public static Specification<Space> filterSpaces(
            String name,
            String mode,
            String createdBy,
            Instant from,
            Instant to
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if (mode != null && !mode.isBlank()) {
                predicates.add(cb.equal(root.get("mode"), mode));
            }

            if (createdBy != null && !createdBy.isBlank()) {
                predicates.add(cb.equal(root.get("createdBy"), createdBy));
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