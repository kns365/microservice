package vn.com.kns.portalapi.data.specification.shop;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.shop.Shop;
import vn.com.kns.portalapi.core.entity.shop.Shop_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ShopSpec {

    private ShopSpec() {
        //empty
    }

    public static Specification<Shop> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(codeLike(f.getCode()))
                .and(nameLike(f.getName()))
                ;
    }

    private static Specification<Shop> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Shop_.id), val);
    }
    
    private static Specification<Shop> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Shop_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Shop> codeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Shop_.code)), "%" + val.toLowerCase() + "%"));
    }

}

   
