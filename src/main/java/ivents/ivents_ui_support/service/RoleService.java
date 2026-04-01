package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.data.RoleData;
import ivents.ivents_ui_support.dto.data.UserData;
import ivents.ivents_ui_support.dto.request.CreateRoleRequest;
import ivents.ivents_ui_support.dto.request.UpdateRoleRequest;
import ivents.ivents_ui_support.dto.response.EntityResponse;
import ivents.ivents_ui_support.dto.response.PaginationResponse;
import ivents.ivents_ui_support.entity.Role;
import ivents.ivents_ui_support.repository.RoleRepository;
import ivents.ivents_ui_support.specification.RoleSpecification;
import ivents.ivents_ui_support.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthUtil authUtil;

    public EntityResponse fetchRoles(int page, int size, String name, String createdBy,
                                     Instant from, Instant to) {

        Pageable pageable = PageRequest.of(page, size);

        // Dynamic null-safe filtering using Specification
        Page<Role> rolesPage = roleRepository.findAll(RoleSpecification.filterRoles(name, createdBy, from, to),
                pageable
        );

        if (rolesPage.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("No Records found.")
                    .build();
        }

        // Map Role to RoleData DTO
        List<RoleData> rolesDataList = rolesPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        // Pagination info
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .currentPage(rolesPage.getNumber())
                .totalPages(rolesPage.getTotalPages())
                .totalItems(rolesPage.getTotalElements())
                .build();

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request processed successfully")
                .data(rolesDataList)
                .paginationResponse(paginationResponse)
                .build();
    }

    public EntityResponse createRole(CreateRoleRequest request) {
        Optional<UserData> userDataOpt = authUtil.getCurrentUser();
        String email = userDataOpt.map(UserData::getEmail).orElse("system");

        Role role = Role.builder()
                .name(request.getName())
                .createdBy(email)
                .modifiedBy(email)
                .createdOn(LocalDateTime.now())
                .modifiedOn(LocalDateTime.now())
                .build();

        roleRepository.save(role);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request Processed Successfully")
                .build();
    }

    public EntityResponse updateRole(UpdateRoleRequest request) {
        Optional<UserData> userDataOpt = authUtil.getCurrentUser();
        String email = userDataOpt.map(UserData::getEmail).orElse("system");

        Optional<Role> existingRoleOpt = roleRepository.findById(request.getId());

        if (existingRoleOpt.isEmpty()) {
            return EntityResponse.builder()
                    .status(false)
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Role not found.")
                    .build();
        }

        Role role = existingRoleOpt.get();
        role.setName(request.getName());
        role.setModifiedBy(email);
        role.setModifiedOn(LocalDateTime.now());

        roleRepository.save(role);

        return EntityResponse.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Request Processed Successfully")
                .build();
    }

    private RoleData mapToDto(Role role) {
        return RoleData.builder()
                .id(role.getId())
                .name(role.getName())
                .createdBy(role.getCreatedBy())
                .modifiedBy(role.getModifiedBy())
                .createdOn((role.getCreatedOn()))
                .modifiedOn(role.getModifiedOn())
                .build();
    }
}