package vn.com.kns.portalapi.application.service.category.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInputDto {
    private Long id;
//    private String code;
    private String name;
    private String barCode;
    private Long groupItemId;
    private Long unitId;
}
