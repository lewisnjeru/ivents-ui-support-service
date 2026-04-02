
package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.request.CreateUserPermissionRequest;
import ivents.ivents_ui_support.dto.request.UpdateUserPermissionRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import ivents.ivents_ui_support.entity.UserPermission;
import ivents.ivents_ui_support.exception.CustomValidationException;
import ivents.ivents_ui_support.repository.UserPermissionRepository;
import ivents.ivents_ui_support.repository.PermissionRepository;
import ivents.ivents_ui_support.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPermissionService {

    private final UserPermissionRepository userPermissionRepository;
    private final PermissionRepository permissionRepository;
    private final AuthUtil authUtil;

@Transactional
public EntityResponse createUserPermission(CreateUserPermissionRequest request) {
    Boolean granted = request.getGranted();
    Long permissionId = request.getPermissionId();
    Long targetUserId = request.getUserId();

    // 1️⃣ Get current user from SecurityContext
    Optional<UserDetailsData> userDataOpt = authUtil.getCurrentUser();
    Long currentUserId = userDataOpt.map(UserDetailsData::getId).orElse(null);

    // 2️⃣ Prevent self-assignment
    if (currentUserId != null && currentUserId.equals(targetUserId)) {
        throw new CustomValidationException("Users cannot assign permissions to themselves");
    }

    // 3️⃣ Validate permission exists in DB
    if (permissionRepository.findById(permissionId).isEmpty()) {
        throw new CustomValidationException("Permission does not exist: " + permissionId);
    }

    // 4️⃣ Check if already exists
    userPermissionRepository.findByUserIdAndPermissionId(targetUserId, permissionId)
            .ifPresent(up -> {
                throw new CustomValidationException("Permission already assigned to this user");
            });

    // 5️⃣ Create the UserPermission
    UserPermission up = UserPermission.builder()
            .userId(targetUserId)
            .permissionId(permissionId)
            .granted(granted)
            .build();

  userPermissionRepository.save(up);
    return EntityResponse.builder()
            .status(true)
            .code(HttpStatus.OK.value())
            .message("UserPermission created successfully")
            .build();
}


    public EntityResponse getUserPermissions(Long userId) {
       List<UserPermission> userPermissionList = userPermissionRepository.findAllByUserId(userId);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .data(userPermissionList)
                .message("Request Processed successfully")
                .build();
    }

    @Transactional
    public EntityResponse updateGranted(UpdateUserPermissionRequest request) {
        UserPermission up = userPermissionRepository.findById(request.getId())
                .orElseThrow(() -> new CustomValidationException("Permission doesn't exist"));

        up.setGranted(request.getGranted());
        userPermissionRepository.save(up);

        return  EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("UserPermission updated")
                .build();
    }
}
