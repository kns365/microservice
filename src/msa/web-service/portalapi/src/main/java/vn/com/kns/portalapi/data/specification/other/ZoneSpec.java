package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.Zone;
import vn.com.kns.portalapi.core.entity.other.Zone_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ZoneSpec {

    private ZoneSpec() {
        //empty
    }

    public static Specification<Zone> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<Zone> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Zone_.id), val);
    }


}

   
