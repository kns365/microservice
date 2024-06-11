package vn.com.kns.portalapi.application.service.category.unit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDto extends BaseEntityDto {
    private String name;
}
