package vn.com.kns.portalapi.data.specification.app;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.app.Role;
import vn.com.kns.portalapi.core.entity.app.Role_;
import vn.com.kns.portalapi.core.entity.app.User_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class RoleSpec {

    private RoleSpec() {
        //empty
    }

    public static Specification<Role> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(nameLike(f.getName()))
                ;
    }

    private static Specification<Role> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(User_.id), val);
    }

    private static Specification<Role> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Role_.name)), "%" + val.toLowerCase() + "%"));
    }

}

   
