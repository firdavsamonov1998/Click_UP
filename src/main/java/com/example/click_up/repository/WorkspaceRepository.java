package com.example.click_up.repository;

import com.example.click_up.entity.UserEntity;
import com.example.click_up.entity.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkSpace, Long> {
    boolean existsByOwnerIdAndName(Long owner_id, String name);
}
