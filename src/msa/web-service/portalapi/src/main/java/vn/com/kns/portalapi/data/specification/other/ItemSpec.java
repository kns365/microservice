package vn.com.kns.portalapi.data.specification.other;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ItemSpec {

    private ItemSpec() {
        //empty
    }

    public static Specification<Item> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                .and(codeLike(f.getCode()))
                .and(nameLike(f.getName()))
                .and(groupItemLike(f.getGroupItem()))
                .and(unitLike(f.getUnit()))
                ;
    }

    private static Specification<Item> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(Item_.id), val);
    }

    private static Specification<Item> nameLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Item_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Item> codeLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Item_.code)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Item> groupItemLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Item_.groupItem).get(GroupItem_.name)), "%" + val.toLowerCase() + "%"));
    }

    private static Specification<Item> unitLike(String val) {
        return ((root, query, cb) -> val == null || val.isEmpty() ? cb.conjunction() : cb.like(cb.lower(root.get(Item_.unit).get(Unit_.name)), "%" + val.toLowerCase() + "%"));
    }

}

   
