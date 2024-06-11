package vn.com.kns.portalapi.application.service.shop.shopExport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopExportMinDto extends BaseEntityDto {
    private String code;
    private Long amount;
    private ShopDto shop;
    private Long shopId;
//    private List<ShopExportDetailMinDto> shopExportDetails;
}
