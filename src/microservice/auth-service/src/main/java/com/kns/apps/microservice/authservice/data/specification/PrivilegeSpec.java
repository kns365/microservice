package com.kns.apps.microservice.authservice.data.specification;

import com.kns.apps.microservice.authservice.core.entity.Privilege;
import com.kns.apps.microservice.authservice.core.entity.Privilege_;
import com.kns.apps.microservice.authservice.core.entity.Role_;
import com.kns.apps.microservice.configserver.core.model.FilterInput;
import org.springframework.data.jpa.domain.Specification;

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

   
