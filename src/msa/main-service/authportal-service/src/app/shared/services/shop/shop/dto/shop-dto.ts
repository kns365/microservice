import {CountryDto} from '../../../category/country/dto/country-dto';
import {ProvinceDto} from '../../../category/province/dto/province-dto';
import {DistrictDto} from '../../../category/district/dto/district-dto';
import {ContactDto} from '../../../contact/dto/contact-dto';

export class ShopDto {
  id: number | null;
  name: string;
  code: string;
  phone: string;
  address: string;
  isActive: boolean;

  country: CountryDto = new CountryDto();
  countryId: number;
  province: ProvinceDto = new ProvinceDto();
  provinceId: number;
  district: DistrictDto = new DistrictDto();
  districtId: number;

  contacts: ContactDto[] = [];

  constructor(id?: number | null, name?: string, code?: string) {
    this.id = id;
    this.name = name;
    this.code = code;
  }
}
