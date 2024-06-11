package vn.com.kns.portalapi.application.service.category.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto extends BaseEntityDto {
    private String name;
    private String email;
    private String phone;
    private String title;//chức danh
    private String alias;//Ông/Bà
    private String contactType;//issue / order

//    private SupplierDto supplier;
    private Long supplierId;

//    private ShopDto shop;
    private Long shopId;
}
