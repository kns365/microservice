package vn.com.kns.portalapi.core.entity.app;

import lombok.*;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_ptl_app_tokenverify")
public class TokenVerify extends BaseEntity {
    private String token;
    private Boolean isVerify;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
