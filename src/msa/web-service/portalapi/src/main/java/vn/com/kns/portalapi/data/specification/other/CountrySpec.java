package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class CountrySpec {

    private CountrySpec() {
        //empty
    }

    public static Specification<Country> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(nameLike(f.getName()))
                .and(codeLike(f.getCode()))
                ;
    }

    private static Specification<Country> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Country_.id), val);
    }

    private static Specification<Country> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Country_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Country> codeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Country_.code)), "%" + val.toLowerCase() + "%"));
    }
}

   
