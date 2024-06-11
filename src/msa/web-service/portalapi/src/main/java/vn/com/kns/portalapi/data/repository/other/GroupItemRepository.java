package vn.com.kns.portalapi.data.repository.other;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.other.GroupItem;

import java.util.Optional;

@Repository
public interface GroupItemRepository extends JpaRepository<GroupItem, Long> {
    Page<GroupItem> findAll(@Nullable Specification<GroupItem> spec, @NonNull Pageable pageable);
    Optional<GroupItem> findByName(String input);
}
