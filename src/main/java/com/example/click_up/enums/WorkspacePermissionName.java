package com.example.click_up.enums;

import java.util.Arrays;
import java.util.List;

import static com.example.click_up.enums.WorkspaceRoleName.*;

public enum WorkspacePermissionName {
    ADD_MEMBER("Add/Remove Member", "Gives add user or Remove", Arrays.asList(
            ROLE_OWNER, ROLE_ADMIN
    )),
    EDITE_STATUS("Edite Status", "Gives Permission",
            Arrays.asList(
                    ROLE_OWNER, ROLE_ADMIN));

    public final String name;
    public final String description;
    public final List<WorkspaceRoleName> workspaceRoleNames;

    WorkspacePermissionName(String name, String description, List<WorkspaceRoleName> workspaceRoleNames) {
        this.name = name;
        this.description = description;
        this.workspaceRoleNames = workspaceRoleNames;
    }

}
