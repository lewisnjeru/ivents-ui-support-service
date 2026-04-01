package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT id FROM roles WHERE name = :name", nativeQuery = true)
    Long findIdByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO role_permissions(role_id, permission_id)
        SELECT :roleId, p.id FROM permissions p
        ON CONFLICT (role_id, permission_id) DO NOTHING
    """, nativeQuery = true)
    void assignAllPermissionsToRole(@Param("roleId") Long roleId);

    Optional<Role> findById(Long id);

    Page<Role> findAll(Specification<Role> roleSpecification, Pageable pageable);
}