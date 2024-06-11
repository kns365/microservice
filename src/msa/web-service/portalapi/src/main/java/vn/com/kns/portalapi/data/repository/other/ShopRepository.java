package vn.com.kns.portalapi.data.repository.other;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.shop.Shop;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Page<Shop> findAll(@Nullable Specification<Shop> spec, @NonNull Pageable pageable);
    Optional<Shop> findByName(String input);
    Optional<Shop> findByCode(String input);
    Optional<Shop> findTopByOrderByIdDesc();
}
