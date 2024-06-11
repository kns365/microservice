package vn.com.kns.portalapi.application.service.category.country.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryInputDto {
    private Long id;
    private String name;
    private String code;
}
