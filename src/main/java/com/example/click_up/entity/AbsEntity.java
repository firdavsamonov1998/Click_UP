package com.example.click_up.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
public abstract class AbsEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp created_date;

    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp update_date;

    @JoinColumn(nullable = false, updatable = false)
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity createdBy;

    @JoinColumn(nullable = false)
    @LastModifiedBy
    @ManyToOne
    private UserEntity updatedBy;


}
