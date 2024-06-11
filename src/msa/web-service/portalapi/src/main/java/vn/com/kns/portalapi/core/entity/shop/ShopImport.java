package vn.com.kns.portalapi.core.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_shopimport")
@EntityListeners(AuditingEntityListener.class)
public class ShopImport extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "shop_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ShopOrder shopOrder;
    @Column(name = "shop_order_id")
    private Long shopOrderId;

    @OneToMany(mappedBy = "shopImport", cascade = CascadeType.ALL)// required CascadeType.ALL , if not detail will not inserted
    private List<ShopImportDetail> shopImportDetails;
}
