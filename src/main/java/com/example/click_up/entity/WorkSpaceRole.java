package com.example.click_up.entity;

import com.example.click_up.enums.WorkspaceRoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "workspace_role")
public class WorkSpaceRole extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpace workSpace;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendRole;
}
