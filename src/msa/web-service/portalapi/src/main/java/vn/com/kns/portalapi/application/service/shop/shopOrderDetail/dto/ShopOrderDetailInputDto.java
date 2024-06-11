package vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderDetailInputDto {
    private Long id;
    private Integer quantity;
    private Long price;
    private String unit;

    private Long shopOrderId;
    private Long supplyId;
}
