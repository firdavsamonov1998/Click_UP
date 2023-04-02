package com.example.click_up.entity;

import com.example.click_up.enums.WorkspacePermissionName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WorkspacePermission extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpaceRole workSpaceRole;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName workspacePermissionName;


}
