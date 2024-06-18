import {ProvinceDto} from '../../province/dto/province-dto';

export class DistrictDto {
  id: number | null;
  name: string;
  provinceId: number | null;
  province: ProvinceDto = new ProvinceDto();

  constructor(id?: number | null, name?: string) {
    this.id = id;
    this.name = name;
  }
}
