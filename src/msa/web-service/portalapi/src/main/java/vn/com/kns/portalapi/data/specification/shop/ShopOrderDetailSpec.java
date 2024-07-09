package vn.com.kns.portalapi.data.specification.shop;

import org.springframework.data.jpa.domain.Specification;
import vn.com.kns.portalapi.core.entity.shop.ShopOrderDetail;
import vn.com.kns.portalapi.core.entity.shop.ShopOrderDetail_;
import vn.com.kns.portalapi.core.model.FilterInput;

public class ShopOrderDetailSpec {

    private ShopOrderDetailSpec() {
        //empty
    }

    public static Specification<ShopOrderDetail> filterBy(FilterInput f) {
        return Specification
                .where(hasId(f.getId()))
                ;
    }

    private static Specification<ShopOrderDetail> hasId(Long val) {
        return (root, query, cb) -> val == null || val == 0 ? cb.conjunction() : cb.equal(root.get(ShopOrderDetail_.id), val);
    }


}

   
