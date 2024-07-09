package vn.com.kns.portalapi.data.specification.shop;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.shop.ShopImport;
import vn.com.kns.portalapi.core.entity.shop.ShopImport_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ShopImportSpec {

    private ShopImportSpec() {
        //empty
    }

    public static Specification<ShopImport> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<ShopImport> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopImport_.id), val);
    }


}

   
