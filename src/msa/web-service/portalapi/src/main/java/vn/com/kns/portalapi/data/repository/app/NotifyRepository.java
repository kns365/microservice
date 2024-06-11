package vn.com.kns.portalapi.data.repository.app;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.app.Notify;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {
    Page<Notify> findAll(@Nullable Specification<Notify> spec, @NonNull Pageable pageable);

    Page<Notify> findAllByUsername(@Nullable Pageable pageable, String username);
}
