package ivents.ivents_ui_support.service;

import ivents.ivents_ui_support.entity.User;
import ivents.ivents_ui_support.repository.UserRepository;
import ivents.ivents_ui_support.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemInitializer implements CommandLineRunner {

    private final PermissionSyncService permissionSyncService;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        // Sync all permissions & roles dynamically
        permissionSyncService.syncRolesWithEnum();

        // Ensure SUPER_ADMIN role exists and has all permissions
        Long superAdminRoleId = permissionSyncService.ensureSuperAdminRole();

        // Seed SUPER_ADMIN user if not exists
        seedSuperAdminUser(superAdminRoleId);
    }

    private void seedSuperAdminUser(Long superAdminRoleId) {
        if (userRepository.findIdByUsername("superadmin") == null) {
            User user = new User();
            user.setUsername("superadmin");
            user.setEmail("superadmin@example.com");
            user.setPassword(PasswordEncoderUtil.encode("SuperSecret123"));
            user.setRoleId(superAdminRoleId);
            userRepository.save(user);
        }
    }
}