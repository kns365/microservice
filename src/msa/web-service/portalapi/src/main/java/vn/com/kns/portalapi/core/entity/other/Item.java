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
@Table(name = "tbl_ptl_item")
@EntityListeners(AuditingEntityListener.class)
public class Item extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String barCode;

    @ManyToOne
    @JoinColumn(name = "groupItem_id", referencedColumnName = "id", insertable = false, updatable = false)
    private GroupItem groupItem;
    @Column(name = "groupItem_id", nullable = false)
    private Long groupItemId;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Unit unit;
    @Column(name = "unit_id")
    private Long unitId;
}
