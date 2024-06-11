package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.Ward;
import vn.com.kns.portalapi.core.entity.Ward_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class WardSpec {

    private WardSpec() {
        //empty
    }

    public static Specification<Ward> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<Ward> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Ward_.id), val);
    }


}

   
