package com.kns.apps.msa.authservice.data.specification;

import com.kns.apps.msa.authservice.core.entity.User;
import com.kns.apps.msa.authservice.core.entity.User_;
import com.kns.apps.msa.commonpack.core.model.FilterInput;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {

    private UserSpec() {
        //empty
    }

    public static Specification<User> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(usernameLike(f.getUsername()))
                .and(fullNameLike(f.getFullName()))
                .and(emailLike(f.getEmail()))
                ;
    }

    private static Specification<User> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(User_.id), val);
    }

    private static Specification<User> usernameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(User_.username)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<User> fullNameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(User_.fullName)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<User> emailLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(User_.email)), "%" + val.toLowerCase() + "%"));
    }

}

   
