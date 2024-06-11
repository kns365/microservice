package vn.com.kns.portalapi.core.entity.other;

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
@Table(name = "tbl_ptl_country")
@EntityListeners(AuditingEntityListener.class)
public class Country extends BaseEntity {
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

//    @OneToMany(mappedBy = "country")
//    private List<Province> provinces;
}
