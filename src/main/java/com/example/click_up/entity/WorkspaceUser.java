package com.example.click_up.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class WorkspaceUser extends AbsEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpace workSpace;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpaceRole workSpaceRole;

    @Column(nullable = false, updatable = false)
    private Timestamp invited_date;

    private Timestamp joined_date;

}
