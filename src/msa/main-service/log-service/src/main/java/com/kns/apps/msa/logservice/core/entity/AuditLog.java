package com.kns.apps.msa.logservice.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_msa_log_auditlog")
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

    private String createdBy;

    private String serviceName;

    private Integer execDuration;
    private String clientIpAddress;
    private String clientName;
    private String browserInfo;
    private String path;

    private String httpStatus;

    @Lob
    private String exception;
    @Lob
    private String input;
    @Lob
    private String output;

}
