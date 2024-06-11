package vn.com.kns.portalapi.application.service.category.province.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceInputDto {
    private Long id;
    private String name;
    private Long countryId;
}
