package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.TeamData;
import ivents.ivents_ui_support.dto.request.CreateTeamRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.entity.Team;
import ivents.ivents_ui_support.entity.UserTeam;
import ivents.ivents_ui_support.repository.TeamRepository;
import ivents.ivents_ui_support.repository.UserTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;

    public EntityResponse createTeam(CreateTeamRequest request) {
        
        Team team = new Team();
        team.setName(request.getName());
        team.setCreatedOn(Instant.now());
        team.setCreatedBy(request.getCreatedBy());
        
        teamRepository.save(team);
        
        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Team created successfully")
                .build();
    }

    public EntityResponse getTeams(int page, int size, Long teamId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Team> teamPage = teamRepository.getTeams(pageable, teamId);
        if (teamPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No teams found")
                    .build();
        }

        List<TeamData> teamDataList = teamPage.getContent()
                .stream()
                .map(this::mapToTeamData)
                .toList();

        PaginationResponse paginationResponse = PaginationResponse.builder().currentPage(teamPage.getNumber())
                .totalPages(teamPage.getTotalPages()).totalItems(teamPage.getTotalElements()).build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .data(teamDataList)
                .message("Request processed successfully")
                .paginationResponse(paginationResponse)
                .build();
    }

    private TeamData mapToTeamData(Team team) {
        List<UserTeam> userTeams = userTeamRepository.findByTeamId(team.getId());

        List<Long> memberIds = userTeams.stream()
                .map(UserTeam::getUserId)
                .toList();

        return TeamData.builder()
                .name(team.getName())
                .createdBy(team.getCreatedBy())
                .createdOn(String.valueOf(team.getCreatedOn()))
                .modifiedBy(team.getModifiedBy())
                .modifiedOn(String.valueOf(team.getModifiedOn()))
                .memberIds(memberIds)
                .build();
    }

}
