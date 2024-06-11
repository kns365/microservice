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
@Table(name = "tbl_ptl_shopexport")
@EntityListeners(AuditingEntityListener.class)
public class ShopExport extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Shop shop;
    @Column(name = "shop_id")
    private Long shopId;

    @OneToMany(mappedBy = "shopExport", cascade = CascadeType.ALL)
    private List<ShopExportDetail> shopExportDetails;
}
