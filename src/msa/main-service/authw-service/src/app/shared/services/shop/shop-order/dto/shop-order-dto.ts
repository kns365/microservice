import {SupplierDto} from '../../../supplier/dto/supplier-dto';
import {ShopDto} from '../../../shop/shop/dto/shop-dto';
import {ShopOrderDetailDto} from '../../shop-order-detail/dto/shop-order-detail-dto';

export class ShopOrderDto {
  id: number | null;
  code: string;
  step: number = 0;
  shopId: number;
  shop: ShopDto = new ShopDto();
  supplierId: number;
  supplier: SupplierDto = new SupplierDto();
  shopOrderDetails: ShopOrderDetailDto[] = [];
}
