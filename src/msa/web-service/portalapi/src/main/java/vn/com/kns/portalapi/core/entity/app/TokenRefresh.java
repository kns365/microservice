package vn.com.kns.portalapi.core.entity.app;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_ptl_app_tokenrefresh")
@EntityListeners(AuditingEntityListener.class)
public class TokenRefresh extends BaseEntity {
    private String username;
    private String token;
    private Instant expiryDate;
}
