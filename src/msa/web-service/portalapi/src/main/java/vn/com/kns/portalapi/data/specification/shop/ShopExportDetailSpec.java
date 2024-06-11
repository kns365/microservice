package vn.com.kns.portalapi.data.specification.shop;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.shop.ShopExportDetail;
import vn.com.kns.portalapi.core.entity.other.ShopExportDetail_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ShopExportDetailSpec {

    private ShopExportDetailSpec() {
        //empty
    }

    public static Specification<ShopExportDetail> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<ShopExportDetail> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopExportDetail_.id), val);
    }


}

   
