package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateUserRequest;
import ivents.ivents_ui_support.dto.request.UpdateUserRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.entity.User;
import ivents.ivents_ui_support.entity.UserTeam;
import ivents.ivents_ui_support.exception.CustomValidationException;
import ivents.ivents_ui_support.repository.UserRepository;
import ivents.ivents_ui_support.repository.UserTeamRepository;
import ivents.ivents_ui_support.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;

    public EntityResponse createUser(CreateUserRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        String encoded = PasswordEncoderUtil.encode(request.getPassword());
        user.setPassword(encoded);
        user.setRoleId(request.getRoleId());

        userRepository.save(user);

        return EntityResponse.builder()
                .code(HttpStatus.OK.value())
                .status(true)
                .message("User created successfully")
                .build();
    }

    public EntityResponse updateUser(UpdateUserRequest request) throws CustomValidationException {

        Optional<User> optionalUser = userRepository.findById(request.getId());
        if (optionalUser.isEmpty()) {
            throw new CustomValidationException("User not found");
        }

        User user = optionalUser.get();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRoleId(request.getRoleId());
        user.setIsActive(request.getIsActive());
        user.setModifiedOn(Instant.now());

        userRepository.save(user);

        return EntityResponse.builder()
                .code(HttpStatus.OK.value())
                .status(true)
                .message("User updated successfully")
                .build();
    }

    public EntityResponse getUsers(int page, int size, Long roleId, Boolean isActive, Long teamId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.getUsers(pageable, roleId, isActive, teamId);
        if (userPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No users found")
                    .build();
        }

        List<UserData> userDataList = userPage.getContent()
                .stream()
                .map(this::mapToUserData)
                .toList();

        PaginationResponse paginationResponse = PaginationResponse.builder().currentPage(userPage.getNumber()).totalPages(userPage.getTotalPages()).totalItems(userPage.getTotalElements()).build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .data(userDataList)
                .message("Request processed successfully")
                .paginationResponse(paginationResponse)
                .build();
    }

    private UserData mapToUserData(User user) {
        List<UserTeam> userTeams = userTeamRepository.findByUserId(user.getId());

        List<Long> teamIds = userTeams.stream()
                .map(UserTeam::getTeamId)
                .toList();

        return UserData.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleId(user.getRoleId())
                .resetPasswordToken(user.getResetPasswordToken())
                .failedLogin(user.getFailedLogin())
                .lockedUntil(user.getLockedUntil())
                .isActive(user.getIsActive())
                .createdBy(user.getCreatedBy())
                .modifiedBy(user.getModifiedBy())
                .createdOn(user.getCreatedOn())
                .modifiedOn(user.getModifiedOn())
                .teamIds(teamIds)
                .build();
    }
}
