package vn.com.kns.portalapi.data.specification.shop;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.shop.ShopExport;
import vn.com.kns.portalapi.core.entity.other.ShopExport_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ShopExportSpec {

    private ShopExportSpec() {
        //empty
    }

    public static Specification<ShopExport> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<ShopExport> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopExport_.id), val);
    }


}

   
