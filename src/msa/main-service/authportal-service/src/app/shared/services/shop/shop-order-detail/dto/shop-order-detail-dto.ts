import {ShopOrderDto} from '../../shop-order/dto/shop-order-dto';
import {SupplyDto} from '../../../category/supply/dto/supply-dto';

export class ShopOrderDetailDto {
  id: number | null;
  price: number = 1;
  quantity: number = 1;
  unit: string;

  shopOrderId: number;
  shopOrder: ShopOrderDto = new ShopOrderDto();

  supplyId: number;
  supply: SupplyDto = new SupplyDto();

  // constructor(id?: number | null, supplyId?: number | null, price?: number | null) {
  //   this.id = id;
  //   this.supplyId = supplyId;
  //   this.price = price;
  // }
}
