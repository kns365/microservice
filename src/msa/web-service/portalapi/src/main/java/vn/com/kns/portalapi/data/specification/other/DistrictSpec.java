package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class DistrictSpec {

    private DistrictSpec() {
        //empty
    }

    public static Specification<District> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(nameLike(f.getName()))
                .and(provinceNameLike(f.getProvince()))
                ;
    }

    private static Specification<District> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(District_.id), val);
    }

    private static Specification<District> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(District_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<District> provinceNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(District_.province).get(Province_.name)), "%" + val.toLowerCase() + "%"));
    }
}

   
