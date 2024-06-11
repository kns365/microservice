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
@Table(name = "tbl_ptl_street")
@EntityListeners(AuditingEntityListener.class)
public class Street extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id", insertable = false, updatable = false)
    private District district;
    @Column(name = "district_id")
    private Long districtId;

    @ManyToOne
    @JoinColumn(name = "ward_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ward ward;
    @Column(name = "ward_id")
    private Long wardId;
}
