package vn.com.kns.portalapi.data.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.shop.ShopExport;

import java.util.Optional;

@Repository
public interface ShopExportRepository extends JpaRepository<ShopExport, Long> {
    Page<ShopExport> findAll(@Nullable Specification<ShopExport> spec, @NonNull Pageable pageable);
    Optional<ShopExport> findByCode(String input);
}
