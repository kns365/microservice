package vn.com.kns.portalapi.application.service.shop.shopExport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto.ShopExportDetailInputDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopExportInputDto {
    private Long id;
    private String code;
    private Long amount;
    private Long shopId;
    private List<ShopExportDetailInputDto> shopExportDetails;
}
