package com.example.click_up.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Entity
@Component
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_id"})})
@EntityListeners(AuditingEntityListener.class)
public class WorkSpace extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @JoinColumn(nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity owner;

    @Column(nullable = false)
    private String initialLetter;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    @PrePersist
    @PreUpdate
    public void initialLetter() {
        this.initialLetter = name.substring(0,1);
    }


}
