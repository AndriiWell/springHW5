package com.muzika.homeworksb.repository;

import com.muzika.homeworksb.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);

    Optional<UserRole> findFirstByName(String name);
}
