package vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderDetailDto extends BaseEntityDto {
    private Integer quantity;
    private Long price;
    private String unit;

    private Long shopOrderId;
    private SupplyDto supply;
    private Long supplyId;
}
