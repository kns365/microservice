package vn.com.kns.portalapi.core.entity.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_supply")
@EntityListeners(AuditingEntityListener.class)
public class Supply extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String barcode;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Item item;
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supplier supplier;
    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Unit unit;
    @Column(name = "unit_id", nullable = false)
    private Long unitId;


//    @ManyToOne
//    @JoinColumn(name = "orderMethod_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private OrderMethod orderMethod;
//    @Column(name = "orderMethod_id")
//    private Long orderMethodId;

//    @ManyToOne
//    @JoinColumn(name = "deliveryMethod_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private DeliveryMethod deliveryMethod;
//    @Column(name = "deliveryMethod_id")
//    private Long deliveryMethodId;

//    @ManyToOne
//    @JoinColumn(name = "returnMethod_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private ReturnMethod returnMethod;
//    @Column(name = "returnMethod_id")
//    private Long returnMethodId;

//    @ManyToOne
//    @JoinColumn(name = "storageCondition_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private StorageCondition storageCondition;
//    @Column(name = "storageCondition_id")
//    private Long storageConditionId;
}
