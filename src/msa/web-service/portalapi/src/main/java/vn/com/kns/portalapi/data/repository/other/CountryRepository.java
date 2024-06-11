package vn.com.kns.portalapi.data.repository.other;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.other.Country;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Page<Country> findAll(@Nullable Specification<Country> spec, @NonNull Pageable pageable);
    Optional<Country> findByName(String input);
}
