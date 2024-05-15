package com.kns.apps.microservice.authservice.data.specification.app;

import com.kns.apps.microservice.authservice.core.entity.Role;
import com.kns.apps.microservice.authservice.core.entity.Role_;
import com.kns.apps.microservice.authservice.core.entity.User_;
import com.kns.apps.microservice.configserver.core.model.FilterInput;
import org.springframework.data.jpa.domain.Specification;

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

   
