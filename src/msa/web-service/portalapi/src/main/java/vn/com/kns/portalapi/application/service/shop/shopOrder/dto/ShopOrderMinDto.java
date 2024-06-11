package vn.com.kns.portalapi.application.service.shop.shopOrder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderMinDto extends BaseEntityDto {
    private String code;
    private Integer step;
    private Date deliveryDate;
    private Boolean isDelivery;

    private SupplierDto supplier;
    private Long supplierId;
    private ShopDto shop;
    private Long shopId;

//    private List<ShopOrderDetailDto> shopOrderDetails;
}
