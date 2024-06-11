package vn.com.kns.portalapi.data.specification.app;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.app.Notify;
import vn.com.kns.portalapi.core.entity.app.User_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class NotifySpec {

    private NotifySpec() {
        //empty
    }

    public static Specification<Notify> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
//                .and(nameLike(f.getName()))
                ;
    }

    private static Specification<Notify> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(User_.id), val);
    }



}

   
