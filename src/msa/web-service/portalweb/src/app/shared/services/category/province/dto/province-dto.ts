import {CountryDto} from '../../country/dto/country-dto';

export class ProvinceDto {
  id: number | null;
  name: string;
  countryId: number | null;
  country: CountryDto = new CountryDto();

  constructor(id?: number | null, name?: string) {
    this.id = id;
    this.name = name;
  }

}
