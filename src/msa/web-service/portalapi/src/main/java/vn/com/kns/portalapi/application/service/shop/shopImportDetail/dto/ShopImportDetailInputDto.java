package vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopImportDetailInputDto {
    private Long id;
    private Integer quantity;
    private Long price;
    private Long shopImportId;
    private Long supplyId;
    private Long shopId;
    private Long supplierId;
}
