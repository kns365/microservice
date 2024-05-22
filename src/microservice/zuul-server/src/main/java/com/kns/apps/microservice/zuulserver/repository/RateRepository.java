package com.kns.apps.microservice.zuulserver.repository;

import com.kns.apps.microservice.zuulserver.entity.Rate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}
