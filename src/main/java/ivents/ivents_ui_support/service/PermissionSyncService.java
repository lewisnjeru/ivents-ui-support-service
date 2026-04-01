package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.role_permission.PermissionEnum;
import ivents.ivents_ui_support.entity.Permission;
import ivents.ivents_ui_support.entity.Role;
import ivents.ivents_ui_support.repository.PermissionRepository;
import ivents.ivents_ui_support.repository.RolePermissionRepository;
import ivents.ivents_ui_support.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionSyncService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

     //Seed any permissions missing in DB from PermissionEnum
    @Transactional
    public void seedPermissions() {
        for (PermissionEnum perm : PermissionEnum.values()) {
            if (permissionRepository.findIdByName(perm.name()) == null) {
                Permission p = new Permission();
                p.setPermissionName(perm.name());
                p.setModule(perm.module);
                p.setSubModule(perm.subModule);
                p.setUri(perm.uri);
                p.setDescription(perm.description);
                p.setVerb(perm.verb);
                permissionRepository.save(p);
            }
        }
    }

     // Get all permissions from PermissionEnum for a role
    public List<String> getPermissionsForRole(String roleName) {
        try {
            return Arrays.stream(PermissionEnum.values())
                    .filter(p -> p.allowedRoles.stream()
                            .anyMatch(tag -> tag.name().equalsIgnoreCase(roleName)))
                    .map(PermissionEnum::name)
                    .toList();
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }
     // Sync all roles with PermissionEnum: add new permissions, remove deprecated ones

    @Transactional
    public void syncRolesWithEnum() {
        // 1️⃣ Ensure all permissions exist
        seedPermissions();

        // 2️⃣ Collect all roles referenced in the enum
        Set<String> rolesInEnum = Arrays.stream(PermissionEnum.values())
                .flatMap(p -> p.allowedRoles.stream())
                .map(Enum::name)
                .collect(Collectors.toSet());

        // 3️⃣ Loop over each role from the enum
        for (String roleName : rolesInEnum) {

            // 3a️⃣ Ensure role exists in DB
            Role role = roleRepository.findByNameIgnoreCase(roleName)
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName(roleName);
                        r.setCreatedBy("SYSTEM");
                        r.setCreatedOn(Instant.now());
                        return roleRepository.save(r);
                    });

            // 3b️⃣ Get permissions allowed for this role in the enum
            List<String> enumPermissions = Arrays.stream(PermissionEnum.values())
                    .filter(p -> p.allowedRoles.stream()
                            .anyMatch(tag -> tag.name().equalsIgnoreCase(roleName)))
                    .map(PermissionEnum::name)
                    .toList();

            // 3c️⃣ Assign missing permissions
            if (!enumPermissions.isEmpty()) {
                rolePermissionRepository.assignPermissionsToRole(role.getId(), enumPermissions);
            }

            // 3d️⃣ Remove deprecated permissions
            List<String> currentPermissions = permissionRepository.findAllPermissionNamesByRoleId(role.getId());
            List<String> toRemove = currentPermissions.stream()
                    .filter(p -> !enumPermissions.contains(p))
                    .toList();

            if (!toRemove.isEmpty()) {
                rolePermissionRepository.removePermissionsFromRole(role.getId(), toRemove);
            }
        }

        // ✅ Roles in DB but not in enum are untouched
    }
     //Ensure SUPER_ADMIN exists and has all permissions
    @Transactional
    public Long ensureSuperAdminRole() {
        Role superAdmin = roleRepository.findByNameIgnoreCase("SUPER_ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("SUPER_ADMIN");
                    r.setCreatedOn(Instant.now());
                    r.setCreatedBy("SYSTEM");
                    return roleRepository.save(r);
                });

        // Assign all permissions dynamically
        List<String> allPermissionNames = Arrays.stream(PermissionEnum.values())
                .map(PermissionEnum::name)
                .toList();
        rolePermissionRepository.assignPermissionsToRole(superAdmin.getId(), allPermissionNames);

        return superAdmin.getId();
    }

}