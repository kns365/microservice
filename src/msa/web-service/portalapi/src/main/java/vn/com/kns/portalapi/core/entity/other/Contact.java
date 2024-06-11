package vn.com.kns.portalapi.core.entity.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.entity.shop.Shop;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_contact")
@EntityListeners(AuditingEntityListener.class)
public class Contact extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String email;
    private String phone;
    private String title;//chức danh
    private String alias;//Ông/Bà
    private String contactType;//issue / order

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supplier supplier;
    @Column(name = "supplier_id")
    private Long supplierId;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Shop shop;
    @Column(name = "shop_id")
    private Long shopId;
}
