package vn.com.kns.portalapi.application.service.shop.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.category.contact.dto.ContactInputDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInputDto {
    private Long id;
//    private String code;
    private String name;
    private String phone;
    private String address;
    private Boolean isActive;

    private Long countryId;
    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private Long streetId;

    private List<ContactInputDto> contacts;
}
