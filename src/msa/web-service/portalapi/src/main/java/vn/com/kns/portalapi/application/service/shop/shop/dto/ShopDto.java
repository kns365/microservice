package vn.com.kns.portalapi.application.service.shop.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.category.contact.dto.ContactDto;
import vn.com.kns.portalapi.application.service.category.country.dto.CountryDto;
import vn.com.kns.portalapi.application.service.category.district.dto.DistrictDto;
import vn.com.kns.portalapi.application.service.category.province.dto.ProvinceDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto extends BaseEntityDto {
    private String code;
    private String name;
    private String phone;
    private String address;
    private Boolean isActive;

    private CountryDto country;
    private Long countryId;
    private ProvinceDto province;
    private Long provinceId;
    private DistrictDto district;
    private Long districtId;
//    private CountryDto country;
    private Long wardId;
//    private CountryDto country;
    private Long streetId;

    private List<ContactDto> contacts;
}
