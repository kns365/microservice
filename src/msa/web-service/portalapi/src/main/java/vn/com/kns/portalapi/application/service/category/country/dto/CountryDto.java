package vn.com.kns.portalapi.application.service.category.country.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.administration.role.dto.RoleDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto extends BaseEntityDto {
    private String name;
    private String code;
}
