package com.example.click_up.entity;

import com.example.click_up.enums.Colors;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity(name = "workspace")
@EntityListeners(AuditingEntityListener.class)
public class WorkSpace extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Colors color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity owner;

    @Column(nullable = false)
    private String initialLetter;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;


}
