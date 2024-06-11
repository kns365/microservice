package vn.com.kns.portalapi.core.entity.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.entity.other.*;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_shop")
@EntityListeners(AuditingEntityListener.class)
public class Shop extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String phone;
    private String address;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;
    @Column(name = "country_id")
    private Long countryId;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Province province;
    @Column(name = "province_id")
    private Long provinceId;

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

    @ManyToOne
    @JoinColumn(name = "street_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Street street;
    @Column(name = "street_id")
    private Long streetId;

    //    @OneToMany(mappedBy = "shop", orphanRemoval = true)
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    @OrderBy("createdDate asc")
    private List<Contact> contacts = new ArrayList<>();
}
