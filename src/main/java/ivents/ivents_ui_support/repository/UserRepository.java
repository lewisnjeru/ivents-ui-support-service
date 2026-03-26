package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository  extends JpaRepository<User, Long> {
    @Query(value = "SELECT id FROM users WHERE username = :username", nativeQuery = true)
    Long findIdByUsername(@Param("username") String username);

}
