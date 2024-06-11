package vn.com.kns.portalapi.core.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.entity.other.Supplier;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_shoporder")
@EntityListeners(AuditingEntityListener.class)
public class ShopOrder extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;
    private Integer step;

    //    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Shop shop;
    @Column(name = "shop_id")
    private Long shopId;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supplier supplier;
    @Column(name = "supplier_id")
    private Long supplierId;

    @OneToMany(mappedBy = "shopOrder", cascade = CascadeType.ALL)
    private List<ShopOrderDetail> shopOrderDetails;
}
