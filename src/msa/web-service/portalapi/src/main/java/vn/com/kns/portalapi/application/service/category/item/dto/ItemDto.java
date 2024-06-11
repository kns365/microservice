package vn.com.kns.portalapi.application.service.category.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.category.groupItem.dto.GroupItemDto;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitDto;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto extends BaseEntityDto {
    private String code;
    private String name;
    private String barCode;

    private GroupItemDto groupItem;
    private Long groupItemId;

    private UnitDto unit;
    private Long unitId;
}
