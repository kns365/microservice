import {ShopImportDetailDto} from '../../shop-import-detail/dto/shop-import-detail-dto';
import {ShopOrderDto} from '../../shop-order/dto/shop-order-dto';

export class ShopImportDto {
  id: number | null;
  code: string;
  shopOrderId: number;
  shopOrder: ShopOrderDto = new ShopOrderDto();
  shopImportDetails: ShopImportDetailDto[] = [];

  constructor(shopOrder?: ShopOrderDto) {
    if (shopOrder) {
      this.shopOrderId = shopOrder.id;
      shopOrder.shopOrderDetails.forEach(p => {
        this.shopImportDetails.push(new ShopImportDetailDto(p.quantity, p.price, p.supplyId, shopOrder.shopId, shopOrder.supplierId));
      })
    }
  }
}
