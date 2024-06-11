package vn.com.kns.portalapi.application.service.shop.shopImport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailInputDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopImportInputDto {
    private Long id;
    private Long shopOrderId;
    private List<ShopImportDetailInputDto> shopImportDetails;
}
