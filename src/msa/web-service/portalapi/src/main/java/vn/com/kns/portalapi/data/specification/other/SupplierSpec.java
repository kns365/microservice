package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class SupplierSpec {

    private SupplierSpec() {
        //empty
    }

    public static Specification<Supplier> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(codeLike(f.getCode()))
                .and(nameLike(f.getName()))
                ;
    }

    private static Specification<Supplier> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Supplier_.id), val);
    }

    private static Specification<Supplier> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supplier_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Supplier> codeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Supplier_.code)), "%" + val.toLowerCase() + "%"));
    }

}

   
