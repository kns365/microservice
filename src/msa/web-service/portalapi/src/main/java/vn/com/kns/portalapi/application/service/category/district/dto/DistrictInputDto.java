package vn.com.kns.portalapi.application.service.category.district.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictInputDto {
    private Long id;
    private String name;
    private Long provinceId;
}
