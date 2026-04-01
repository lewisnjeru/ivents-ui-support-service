package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeamRepository  extends JpaRepository<UserTeam, Long> {
   List<UserTeam> findByUserId(Long id);

    List<UserTeam> findByTeamId(Long id);
}
