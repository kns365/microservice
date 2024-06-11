package vn.com.kns.portalapi.application.service.shop.shopImport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderMinDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopImportMinDto extends BaseEntityDto {
    private String code;
    private ShopOrderMinDto shopOrder;
    private Long shopOrderId;
//    private List<ShopImportDetailDto> shopImportDetails;
}
