package com.kns.apps.msa.authservice.data.repository;

import com.kns.apps.msa.authservice.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(@Nullable Specification<User> spec, @NonNull Pageable pageable);
    Optional<User> findByUsername(String username);
}
