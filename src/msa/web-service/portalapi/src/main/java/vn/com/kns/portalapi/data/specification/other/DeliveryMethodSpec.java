package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.DeliveryMethod;
import vn.com.kns.portalapi.core.entity.DeliveryMethod_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class DeliveryMethodSpec {

    private DeliveryMethodSpec() {
        //empty
    }

    public static Specification<DeliveryMethod> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<DeliveryMethod> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(DeliveryMethod_.id), val);
    }


}

   
