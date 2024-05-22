package com.kns.apps.microservice.zuulserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rate")
public class Rate {
    @Id
    private String rateKey;
    private Long remaining;
    private Long remainingQuota;
    private Long reset;
    private Date expiration;
}
