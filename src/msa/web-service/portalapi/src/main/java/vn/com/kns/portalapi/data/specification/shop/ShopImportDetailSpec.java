package vn.com.kns.portalapi.data.specification.shop;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.Item_;
import vn.com.kns.portalapi.core.entity.other.Supplier_;
import vn.com.kns.portalapi.core.entity.other.Supply_;
import vn.com.kns.portalapi.core.entity.shop.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ShopImportDetailSpec {

    private ShopImportDetailSpec() {
        //empty
    }

    public static Specification<ShopImportDetail> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(supply_BarcodeLike(f.getBarcode()))
                .and(supply_item_CodeLikeOrNameLike(f.getItem()))
                .and(shopImport_CodeLike(f.getShopImport()))
                .and(shopImport_shopOrder_CodeLike(f.getShopOrder()))
                .and(supplier_CodeLikeOrNameLike(f.getSupplier()))
                .and(hasShopId(f.getShopId()))
                .and(shop_NameLike(f.getShop()))
                .and(hasQuantityGreaterThanOrEqualTo(f.getQuantity()))
                .and(hasPriceGreaterThanOrEqualTo(f.getPrice()))
                ;
    }

    private static Specification<ShopImportDetail> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopImportDetail_.id), val);
    }

    private static Specification<ShopImportDetail> shopImport_CodeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(ShopImportDetail_.shopImport).get(ShopImport_.code)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<ShopImportDetail> shopImport_shopOrder_CodeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(ShopImportDetail_.shopImport).get(ShopImport_.shopOrder).get(ShopOrder_.code)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<ShopImportDetail> supplier_CodeLikeOrNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction()
                : cb.or(cb.like(cb.lower(root.get(ShopImportDetail_.supplier).get(Supplier_.code)), "%" + val.toLowerCase() + "%"),
                cb.like(cb.lower(root.get(ShopImportDetail_.supplier).get(Supplier_.name)), "%" + val.toLowerCase() + "%")));
    }

    private static Specification<ShopImportDetail> hasShopId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopImportDetail_.shopId), val);
    }

    private static Specification<ShopImportDetail> supply_BarcodeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(ShopImportDetail_.supply).get(Supply_.barcode)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<ShopImportDetail> shop_NameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(ShopImportDetail_.shop).get(Shop_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<ShopImportDetail> supply_item_CodeLikeOrNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction()
                : cb.or(cb.like(cb.lower(root.get(ShopImportDetail_.supply).get(Supply_.item).get(Item_.code)), "%" + val.toLowerCase() + "%"),
                cb.like(cb.lower(root.get(ShopImportDetail_.supply).get(Supply_.item).get(Item_.name)), "%" + val.toLowerCase() + "%")));
    }

    private static Specification<ShopImportDetail> hasQuantityGreaterThanOrEqualTo(Integer val) {
        return ((root, query, cb) -> val == null || val <= 0 ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get(ShopImportDetail_.quantity), val));
    }

    private static Specification<ShopImportDetail> hasPriceGreaterThanOrEqualTo(Long val) {
        return ((root, query, cb) -> val == null || val <= 0 ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get(ShopImportDetail_.price), val));
    }

}

   
