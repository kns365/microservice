package vn.com.kns.portalapi.application.service.category.district.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.category.province.dto.ProvinceDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto extends BaseEntityDto {
    private String name;
    private ProvinceDto province;
    private Long provinceId;
}
