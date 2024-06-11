package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class SupplySpec {

    private SupplySpec() {
        //empty
    }

    public static Specification<Supply> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(hasSupplierId(f.getSupplierId()))
                .and(itemCodeLike(f.getCode()))
                .and(itemNameLike(f.getName()))
                .and(unitLike(f.getUnit()))
                .and(barCodeLike(f.getBarcode()))
                ;
    }

    private static Specification<Supply> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Supply_.id), val);
    }

    private static Specification<Supply> hasSupplierId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Supply_.supplierId), val);
    }

    private static Specification<Supply> barCodeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supply_.barcode)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Supply> itemNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supply_.item).get((Item_.name))), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Supply> itemCodeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supply_.item).get((Item_.code))), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Supply> unitLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supply_.unit).get(Unit_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Supply> supplierLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supply_.supplier).get(Supplier_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Supply> itemLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supply_.item).get(Item_.name)), "%" + val.toLowerCase() + "%"));
    }

}

   
