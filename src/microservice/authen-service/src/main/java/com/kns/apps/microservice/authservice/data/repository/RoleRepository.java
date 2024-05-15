package com.kns.apps.microservice.authservice.data.repository;

import com.kns.apps.microservice.authservice.core.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Page<Role> findAll(@Nullable Specification<Role> spec, @NonNull Pageable pageable);
    Optional<Role> findByName(String input);
}
