package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long id);

    Page<Role> findAll(Specification<Role> roleSpecification, Pageable pageable);

    Optional<Role> findByNameIgnoreCase(String name);

}