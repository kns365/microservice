package vn.com.kns.portalapi.data.repository.other;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.other.Supply;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Page<Supply> findAll(@Nullable Specification<Supply> spec, @NonNull Pageable pageable);
    List<Supply> findAllBySupplierId(Long input);
    Optional<Supply> findByItemId(Long input);
    Optional<Supply> findByItem_Code(String itemCode);
}
