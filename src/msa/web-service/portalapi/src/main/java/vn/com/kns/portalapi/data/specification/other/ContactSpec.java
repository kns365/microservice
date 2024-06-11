package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.Contact;
import vn.com.kns.portalapi.core.entity.Contact_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ContactSpec {

    private ContactSpec() {
        //empty
    }

    public static Specification<Contact> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<Contact> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Contact_.id), val);
    }


}

   
