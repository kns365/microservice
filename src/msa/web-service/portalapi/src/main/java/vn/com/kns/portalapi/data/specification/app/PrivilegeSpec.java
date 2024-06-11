package vn.com.kns.portalapi.data.specification.app;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.app.Privilege;
import vn.com.kns.portalapi.core.entity.app.Privilege_;
import vn.com.kns.portalapi.core.entity.app.Role_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class PrivilegeSpec {

    private PrivilegeSpec() {
        //empty
    }

    public static Specification<Privilege> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(nameLike(f.getName()))
                ;
    }

    private static Specification<Privilege> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Role_.id), val);
    }

    private static Specification<Privilege> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Privilege_.name)), "%" + val.toLowerCase() + "%"));
    }


}

   
