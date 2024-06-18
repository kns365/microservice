import {ShopExportDto} from '../../shop-export/dto/shop-export-dto';
import {SupplyDto} from '../../../category/supply/dto/supply-dto';

export class ShopExportDetailDto {
  id: number | null;
  quantity: number;
  price: number;
  supplyId: number;
  supply: SupplyDto = new SupplyDto();
  shopExportId: number;
  shopExport: ShopExportDto = new ShopExportDto();

  constructor(supply?: SupplyDto) {
    this.supply = supply;
  }
}
