package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Modifying
    @Query(value = """
        INSERT INTO role_permissions(role_id, permission_id)
        SELECT :roleId, p.id
        FROM permissions p
        WHERE p.permission_name IN :permissionNames
        ON CONFLICT (role_id, permission_id) DO NOTHING
        """, nativeQuery = true)
    void assignPermissionsToRole(@Param("roleId") Long roleId,
                                 @Param("permissionNames") List<String> permissionNames);

    @Modifying
    @Query(value = """
        DELETE FROM role_permissions rp
        USING permissions p
        WHERE rp.permission_id = p.id
          AND rp.role_id = :roleId
          AND p.permission_name IN :permissionNames
        """, nativeQuery = true)
    void removePermissionsFromRole(@Param("roleId") Long roleId,
                                   @Param("permissionNames") List<String> permissionNames);
}