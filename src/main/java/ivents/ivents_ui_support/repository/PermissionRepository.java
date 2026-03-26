package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermissionName(String name);

    @Query(value = "SELECT id FROM permissions WHERE permission_name = :permissionName", nativeQuery = true)
    Long findIdByName(@Param("permissionName") String permissionName);
}