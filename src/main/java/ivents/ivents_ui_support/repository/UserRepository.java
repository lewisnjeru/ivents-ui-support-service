package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository  extends JpaRepository<User, Long> {
    @Query(value = "SELECT id FROM users WHERE username = :username", nativeQuery = true)
    Long findIdByUsername(@Param("username") String username);

    @Query(
            value = """
            SELECT u.* 
            FROM users u
            LEFT JOIN roles ur ON u.id = ur.id
            LEFT JOIN user_teams ut ON u.id = ut.user_id
            WHERE (:roleId IS NULL OR ur.id = :roleId)
              AND (:isActive IS NULL OR u.is_active = :isActive)
              AND (:teamId IS NULL OR ut.team_id = :teamId)
            """,
            countQuery = """
            SELECT COUNT(DISTINCT u.id) 
            FROM users u
            LEFT JOIN roles ur ON u.id = ur.id
            LEFT JOIN user_teams ut ON u.id = ut.user_id
            WHERE (:roleId IS NULL OR ur.id = :roleId)
              AND (:isActive IS NULL OR u.is_active = :isActive)
              AND (:teamId IS NULL OR ut.team_id = :teamId)
            """,
            nativeQuery = true
    )
    Page<User> getUsers(
            Pageable pageable,
            @Param("roleId") Long roleId,
            @Param("isActive") Boolean isActive,
            @Param("teamId") Long teamId

    );}
