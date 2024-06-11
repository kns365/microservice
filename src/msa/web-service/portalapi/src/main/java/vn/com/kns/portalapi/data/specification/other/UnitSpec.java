package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class UnitSpec {

    private UnitSpec() {
        //empty
    }

    public static Specification<Unit> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(nameLike(f.getName()))
                ;
    }

    private static Specification<Unit> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Unit_.id), val);
    }

    private static Specification<Unit> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Unit_.name)), "%" + val.toLowerCase() + "%"));
    }

}

   
