package com.example.click_up.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity(name = "attach")
@EntityListeners(AuditingEntityListener.class)
public class Attachment extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String contentType;

}
