package vn.com.kns.portalapi.core.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.entity.other.Supplier;
import vn.com.kns.portalapi.core.entity.other.Supply;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_shopimportdetail")
@EntityListeners(AuditingEntityListener.class)
public class ShopImportDetail extends BaseEntity {
    private Integer quantity;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "shop_import_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ShopImport shopImport;
    @Column(name = "shop_import_id")
    private Long shopImportId;

    @ManyToOne
    @JoinColumn(name = "supply_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supply supply;
    @Column(name = "supply_id")
    private Long supplyId;

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

//    private Integer quantityOrder;
//    private Integer quantityImport;
//    private Integer quantityReturn;
}
