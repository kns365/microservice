import {ShopDto} from '../../shop/dto/shop-dto';
import {ShopExportDetailDto} from '../../shop-export-detail/dto/shop-export-detail-dto';

export class ShopExportDto {
  id: number | null;
  code: string;
  amount: number;
  shopId: number;
  shop: ShopDto = new ShopDto();
  shopExportDetails: ShopExportDetailDto[] = [];
}
