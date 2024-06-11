package vn.com.kns.portalapi.application.service.category.province.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.category.country.dto.CountryDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto extends BaseEntityDto {
    private String name;
    private CountryDto country;
    private Long countryId;

//    private ZoneDto zone;
//    private Long zoneId;
}
