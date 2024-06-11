package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ProvinceSpec {

    private ProvinceSpec() {
        //empty
    }

    public static Specification<Province> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(nameLike(f.getName()))
                .and(countryNameLike(f.getCountry()))
                ;
    }

    private static Specification<Province> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Province_.id), val);
    }

    private static Specification<Province> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Province_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Province> countryNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Province_.country).get(Country_.name)), "%" + val.toLowerCase() + "%"));
    }


}

   
