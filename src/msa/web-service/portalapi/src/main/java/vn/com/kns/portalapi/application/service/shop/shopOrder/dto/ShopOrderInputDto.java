package vn.com.kns.portalapi.application.service.shop.shopOrder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto.ShopOrderDetailInputDto;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderInputDto {
    private Long id;
    //    private String code;
    private Integer step;
    private Date deliveryDate;
    private Boolean isDelivery;
    private Long supplierId;
    private Long shopId;
    private List<ShopOrderDetailInputDto> shopOrderDetails;
}
