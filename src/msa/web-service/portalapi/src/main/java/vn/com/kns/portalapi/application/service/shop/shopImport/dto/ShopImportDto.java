package vn.com.kns.portalapi.application.service.shop.shopImport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailMinDto;
import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderMinDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopImportDto extends BaseEntityDto {
    private String code;
    private ShopOrderMinDto shopOrder;
    private Long shopOrdserId;
    private List<ShopImportDetailMinDto> shopImportDetails;
}
