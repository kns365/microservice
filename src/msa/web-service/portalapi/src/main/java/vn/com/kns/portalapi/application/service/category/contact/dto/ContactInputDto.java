package vn.com.kns.portalapi.application.service.category.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInputDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String title;//chức danh
    private String alias;//Ông/Bà

    private String contactType;//issue / order

    private Long shopId;
    private Long supplierId;
}
