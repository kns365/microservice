package vn.com.kns.portalapi.data.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.shop.ShopExportDetail;

@Repository
public interface ShopExportDetailRepository extends JpaRepository<ShopExportDetail, Long> {
    Page<ShopExportDetail> findAll(@Nullable Specification<ShopExportDetail> spec, @NonNull Pageable pageable);
}
