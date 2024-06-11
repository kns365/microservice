package vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopExportDetailInputDto {
    private Long id;
    private Integer quantity;
    private Long price;
    private Long supplyId;
    private Long shopExportId;
}
