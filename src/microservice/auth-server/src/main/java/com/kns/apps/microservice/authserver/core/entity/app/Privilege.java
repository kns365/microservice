package com.kns.apps.microservice.authserver.core.entity.app;

import com.kns.apps.microservice.configserver.core.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_msa_aut_privilege")
@EntityListeners(AuditingEntityListener.class)
public class Privilege extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
