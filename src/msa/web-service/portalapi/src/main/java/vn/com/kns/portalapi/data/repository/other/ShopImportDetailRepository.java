package vn.com.kns.portalapi.data.repository.other;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.other.Supply;
import vn.com.kns.portalapi.core.entity.shop.ShopImportDetail;

import java.util.List;

@Repository
public interface ShopImportDetailRepository extends JpaRepository<ShopImportDetail, Long> {
    Page<ShopImportDetail> findAll(@Nullable Specification<ShopImportDetail> spec, @NonNull Pageable pageable);

    List<ShopImportDetail> findDistinctByShopId(Long shopId);

    @Query(value = "select distinct p.supplyId,p.supply,p.supply.item,p.supply.itemId from ShopImportDetail p where p.shopId=?1")
    List<Object[]> findAllSupplyByShopId(Long shopId);

    @Query(value = "select p.id as id,p.supplyId as supplyId,p.supply.itemId as itemId from ShopImportDetail p where p.shopId=?1")
    List<ShopImportDetail> findAllSupplyByShopId2(Long shopId);

//    @Query("SELECT new com.baeldung.aggregation.model.custom.CommentCount(c.year, COUNT(c.year)) "
//            + "FROM Comment AS c GROUP BY c.year ORDER BY c.year DESC")
//    List<CommentCount> countTotalCommentsByYearClass();
}
