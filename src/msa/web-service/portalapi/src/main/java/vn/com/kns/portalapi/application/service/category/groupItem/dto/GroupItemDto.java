package vn.com.kns.portalapi.application.service.category.groupItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.category.country.dto.CountryDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupItemDto extends BaseEntityDto {
    private String name;
}
