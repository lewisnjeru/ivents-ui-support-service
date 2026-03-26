package ivents.ivents_ui_support.dto.role_permission;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum PermissionEnum {
    CLIENT_SELF_ONBOARD("CLIENT_MANAGEMENT", "CLIENT_MANAGEMENT", "/onboarding-service/v1/self-onboarding", "Self Onboard", "POST", List.of(PermissionTag.SUPER_ADMIN)),
    CLIENT_VIEW("CLIENT_MANAGEMENT", "CLIENT_MANAGEMENT", "/onboarding-service/v1/view", "View Client", "GET", List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),;


    public final String module;
    public final String subModule;
    public final String uri;
    public final String description;
    public final String verb;
    public final List<PermissionTag> allowedRoles;
}