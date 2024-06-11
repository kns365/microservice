package vn.com.kns.portalapi.data.specification.shop;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.Supplier_;
import vn.com.kns.portalapi.core.entity.shop.ShopOrder;
import vn.com.kns.portalapi.core.entity.shop.ShopOrder_;
import vn.com.kns.portalapi.core.entity.shop.Shop_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ShopOrderSpec {

    private ShopOrderSpec() {
        //empty
    }

    public static Specification<ShopOrder> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(hasSupplierId(f.getSupplierId()))
                .and(supplier_NameLike(f.getSupplier()))
                .and(hasShopId(f.getShopId()))
                .and(shop_NameLike(f.getShop()))
                .and(codeLike(f.getCode()))
                .and(hasStep(f.getStep()))
                ;
    }

    private static Specification<ShopOrder> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopOrder_.id), val);
    }

    private static Specification<ShopOrder> hasSupplierId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopOrder_.supplierId), val);
    }

    private static Specification<ShopOrder> hasShopId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopOrder_.shopId), val);
    }

    private static Specification<ShopOrder> hasStep(Integer val) {
        return (root, query, cb) -> val == null || val < 0 ? cb.conjunction() : cb.equal(root.get(ShopOrder_.step), val);
    }

    private static Specification<ShopOrder> codeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(ShopOrder_.code)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<ShopOrder> shop_NameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(ShopOrder_.shop).get(Shop_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<ShopOrder> supplier_NameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(ShopOrder_.supplier).get(Supplier_.name)), "%" + val.toLowerCase() + "%"));
    }
}

   
