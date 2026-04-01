package ivents.ivents_ui_support.dto.role_permission;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum PermissionEnum {
    // User Management
    GET_USERS(Module.IVENTS.toString(), SubModule.USER.toString(), "/ivents/v1/users/get", "Get Users", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),
    CREATE_USERS(Module.IVENTS.toString(), SubModule.USER.toString(), "/ivents/v1/users/create", "Create User", Verb.POST.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),
    UPDATE_USERS(Module.IVENTS.toString(), SubModule.USER.toString(), "/ivents/v1/users/update", "Update User", Verb.PUT.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),

    // Spaces
    GET_SPACES(Module.IVENTS.toString(), SubModule.SPACE.toString(), "/ivents/v1/spaces/get", "Get Spaces", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),
    CREATE_SPACES(Module.IVENTS.toString(), SubModule.SPACE.toString(), "/ivents/v1/spaces/create", "Create Space", Verb.POST.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),

    // Tasks
    GET_TASKS(Module.IVENTS.toString(), SubModule.TASK.toString(), "/ivents/v1/tasks/get", "Get Tasks", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),
    CREATE_TASKS(Module.IVENTS.toString(), SubModule.TASK.toString(), "/ivents/v1/tasks/create", "Create Task", Verb.POST.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),
    UPDATE_TASKS(Module.IVENTS.toString(), SubModule.TASK.toString(), "/ivents/v1/tasks/update", "Update Task", Verb.PUT.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),

    // Comments
    GET_COMMENTS(Module.IVENTS.toString(), SubModule.COMMENT.toString(), "/ivents/v1/comments/get", "Get Comments", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),
    CREATE_COMMENTS(Module.IVENTS.toString(), SubModule.COMMENT.toString(), "/ivents/v1/comments/create", "Create Comment", Verb.POST.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),

    // Intake Requests
    GET_INTAKE_REQUESTS(Module.IVENTS.toString(), SubModule.INTAKE_REQUEST.toString(), "/ivents/v1/intake-requests/get", "Get Intake Requests", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),
    CREATE_INTAKE_REQUESTS(Module.IVENTS.toString(), SubModule.INTAKE_REQUEST.toString(), "/ivents/v1/intake-requests/create", "Create Intake Request", Verb.POST.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),

    // Notifications
    GET_NOTIFICATIONS(Module.IVENTS.toString(), SubModule.NOTIFICATION.toString(), "/ivents/v1/notifications/get", "Get Notifications", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),

    // Teams
    GET_TEAMS(Module.IVENTS.toString(), SubModule.SPACE.toString(), "/ivents/v1/teams/get", "Get Teams", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),
    CREATE_TEAM(Module.IVENTS.toString(), SubModule.SPACE.toString(), "/ivents/v1/teams/create", "Create Team", Verb.POST.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN, PermissionTag.USER)),

    //roles
    GET_ROLES(Module.IVENTS.toString(), SubModule.ROLE.toString(), "/ivents/v1/roles/get", "Get Roles", Verb.GET.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),
    CREATE_ROLES(Module.IVENTS.toString(), SubModule.ROLE.toString(), "/ivents/v1/roles/create", "Create Role", Verb.POST.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN)),
    UPDATE_ROLES(Module.IVENTS.toString(), SubModule.ROLE.toString(), "/ivents/v1/roles/update", "Update Role", Verb.PUT.toString(), List.of(PermissionTag.SUPER_ADMIN, PermissionTag.ADMIN));

    public final String module;
    public final String subModule;
    public final String uri;
    public final String description;
    public final String verb;
    public final List<PermissionTag> allowedRoles;

}