package vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.shop.shopImport.dto.ShopImportMinDto;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierDto;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopImportDetailDto extends BaseEntityDto {
    private Integer quantity;
    private Long price;

    private ShopImportMinDto shopImport;
    private Long shopImportId;

    private SupplyDto supply;
    private Long supplyId;

    private ShopDto shop;
    private Long shopId;

    private SupplierDto supplier;
    private Long supplierId;
}
