package vn.com.kns.portalapi.core.entity.app;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.web.component.AttributeEncryptor;
import vn.com.kns.portalapi.web.component.StringCryptoConverter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_app_auditlog")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(updatable = false)
    @CreatedDate
    private Date createdDate;

    @CreatedBy
    private String createdBy;

    private Integer execDuration;
    private String clientIpAddress;
    private String clientName;
    private String browserInfo;
    private String path;

    private String httpStatus;

    @Lob
    private String exception;
    //    @Convert(converter = AttributeEncryptor.class)
//    @Convert(converter = StringCryptoConverter.class)
    @Lob
    private String input;
    @Lob
    private String output;

}
