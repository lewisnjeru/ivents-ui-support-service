package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermissionName(String name);

    @Query("SELECT p.id FROM Permission p WHERE p.permissionName = :name")
    Long findIdByName(@Param("name") String name);

    @Query("SELECT p.permissionName FROM Permission p " +
            "JOIN RolePermission rp ON rp.permissionId = p.id " +
            "WHERE rp.roleId = :roleId")
    List<String> findAllPermissionNamesByRoleId(@Param("roleId") Long roleId);

    Optional<Permission> findById(Long id);
}