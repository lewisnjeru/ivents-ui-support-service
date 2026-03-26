package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.dto.role_permission.PermissionEnum;
import ivents.ivents_ui_support.entity.Permission;
import ivents.ivents_ui_support.entity.Role;
import ivents.ivents_ui_support.entity.User;
import ivents.ivents_ui_support.repository.PermissionRepository;
import ivents.ivents_ui_support.repository.RoleRepository;
import ivents.ivents_ui_support.repository.UserRepository;
import ivents.ivents_ui_support.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        seedPermissions();
        Long superAdminRoleId = seedSuperAdminRole();
        seedSuperAdminUser(superAdminRoleId);
    }

    private void seedPermissions() {
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

    private Long seedSuperAdminRole() {
        Long roleId = roleRepository.findIdByName("SUPER_ADMIN");
        if (roleId == null) {
            Role role = new Role();
            role.setName("SUPER_ADMIN");
            roleId = roleRepository.save(role).getId();
        }
        // assign all permissions to SUPER_ADMIN
        roleRepository.assignAllPermissionsToRole(roleId);
        return roleId;
    }

    private void seedSuperAdminUser(Long superAdminRoleId) {
        Long userId = userRepository.findIdByUsername("superadmin");
        if (userId == null) {
            User user = new User();
            user.setUsername("superadmin");
            user.setEmail("superadmin@example.com");

            String rawPassword = "SuperSecret123";
            String encoded = PasswordEncoderUtil.encode(rawPassword);

            user.setPassword(encoded);
            user.setRoleId(superAdminRoleId);
            userRepository.save(user);
        }
    }
}