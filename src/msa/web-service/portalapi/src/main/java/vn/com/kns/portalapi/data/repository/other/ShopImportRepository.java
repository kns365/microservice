package vn.com.kns.portalapi.data.repository.other;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.shop.ShopImport;

import java.util.Optional;

@Repository
public interface ShopImportRepository extends JpaRepository<ShopImport, Long> {
    Page<ShopImport> findAll(@Nullable Specification<ShopImport> spec, @NonNull Pageable pageable);
    Optional<ShopImport> findByCode(String input);
}
