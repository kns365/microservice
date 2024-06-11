package vn.com.kns.portalapi.application.service.supply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.category.item.dto.ItemDto;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitDto;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyDto extends BaseEntityDto {
    private Long price;

    private ItemDto item;
    private Long itemId;

    private SupplierDto supplier;
    private Long supplierId;

    private UnitDto unit;
    private Long unitId;

    private String barcode;
}
