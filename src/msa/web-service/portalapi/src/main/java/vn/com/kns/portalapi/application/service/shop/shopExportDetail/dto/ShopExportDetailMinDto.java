package vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopExportDetailMinDto extends BaseEntityDto {
    private Integer quantity;
    private Long price;

    private SupplyDto supply;
    private Long supplyId;

//    private ShopExportMinDto shopExport;
    private Long shopExportId;
}
