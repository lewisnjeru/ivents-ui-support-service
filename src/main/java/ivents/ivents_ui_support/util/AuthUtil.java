package ivents.ivents_ui_support.util;

import ivents.ivents_ui_support.dto.data.UserData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtil {

    public Optional<UserData> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(UserData.class::isInstance)
                .map(UserData.class::cast);
    }
}
