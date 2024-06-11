package vn.com.kns.portalapi.application.service.supply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyInputDto {
    private Long id;
    private Long price;
    private Long itemId;
    private Long supplierId;
    private Long unitId;
    private String barcode;
}
