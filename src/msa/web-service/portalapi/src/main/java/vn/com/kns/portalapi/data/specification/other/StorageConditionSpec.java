package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.StorageCondition;
import vn.com.kns.portalapi.core.entity.other.StorageCondition_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class StorageConditionSpec {

    private StorageConditionSpec() {
        //empty
    }

    public static Specification<StorageCondition> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<StorageCondition> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(StorageCondition_.id), val);
    }


}

   
