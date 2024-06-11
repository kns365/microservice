package vn.com.kns.portalapi.application.service.notify.dto;

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
public class NotifyDto extends BaseEntityDto {
    private String template;
    private String username;
    private String data;
    private Boolean state;
}
