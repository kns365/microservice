package vn.com.kns.portalapi.core.entity.other;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_province")
@EntityListeners(AuditingEntityListener.class)
public class Province extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;
    @Column(name = "country_id")
    private Long countryId;

    @ManyToOne
    @JoinColumn(name = "zone_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Zone zone;
    @Column(name = "zone_id")
    private Long zoneId;
}
