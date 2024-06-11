package vn.com.kns.portalapi.core.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.entity.other.Supply;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_shoporderdetail")
@EntityListeners(AuditingEntityListener.class)
public class ShopOrderDetail extends BaseEntity {
    private Integer quantity;
    private Long price;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "shop_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ShopOrder shopOrder;
    @Column(name = "shop_order_id")
    private Long shopOrderId;

    @ManyToOne
    @JoinColumn(name = "supply_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supply supply;
    @Column(name = "supply_id")
    private Long supplyId;

}
