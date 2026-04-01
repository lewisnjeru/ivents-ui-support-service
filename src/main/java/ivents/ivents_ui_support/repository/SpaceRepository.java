package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    Page<Space> findAll(Specification<Space> spaceSpecification, Pageable paging);
}