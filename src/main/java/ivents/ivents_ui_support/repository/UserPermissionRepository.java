package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    List<UserPermission> findAllByUserId(Long userId);

    Optional<UserPermission> findByUserIdAndPermissionId(Long userId, Long permissionId);
}
