package ivents.ivents_ui_support.repository;

import ivents.ivents_ui_support.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository  extends JpaRepository<Team, Long> {


    @Query(
            value = "SELECT * FROM teams t WHERE (:teamId IS NULL OR t.id = :teamId)",
            countQuery = "SELECT count(*) FROM teams t WHERE (:teamId IS NULL OR t.id = :teamId)",
            nativeQuery = true
    )
    Page<Team> getTeams(@Param("teamId")  Pageable pageable, Long teamId);}
