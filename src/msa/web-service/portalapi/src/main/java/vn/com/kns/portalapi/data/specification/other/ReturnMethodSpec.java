package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.ReturnMethod;
import vn.com.kns.portalapi.core.entity.ReturnMethod_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ReturnMethodSpec {

    private ReturnMethodSpec() {
        //empty
    }

    public static Specification<ReturnMethod> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<ReturnMethod> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ReturnMethod_.id), val);
    }


}

   
