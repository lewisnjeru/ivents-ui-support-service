package ivents.ivents_ui_support.util;

import ivents.ivents_ui_support.dto.role_permission.UserDetailsData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtil {

    public Optional<UserDetailsData> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(UserDetailsData.class::isInstance)
                .map(UserDetailsData.class::cast);
    }
}
