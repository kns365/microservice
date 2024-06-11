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
@Table(name = "tbl_ptl_groupitem")
@EntityListeners(AuditingEntityListener.class)
public class GroupItem extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

//    @OneToMany(mappedBy = "groupItem")
//    private List<Item> items;
}
