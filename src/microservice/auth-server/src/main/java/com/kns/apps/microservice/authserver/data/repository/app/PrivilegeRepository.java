package com.kns.apps.microservice.authserver.data.repository.app;

import com.kns.apps.microservice.authserver.core.entity.app.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Page<Privilege> findAll(@Nullable Specification<Privilege> spec, @NonNull Pageable pageable);
    Optional<Privilege> findByName(String input);
}
