import {ShopImportDto} from '../../shop-import/dto/shop-import-dto';
import {ShopDto} from '../../../shop/shop/dto/shop-dto';
import {SupplierDto} from '../../../supplier/dto/supplier-dto';
import {SupplyDto} from '../../../category/supply/dto/supply-dto';

export class ShopImportDetailDto {
  id: number | null;
  quantity: number;
  price: number;
  shopImportId: number;
  shopImport: ShopImportDto = new ShopImportDto();
  supplyId: number;
  supply: SupplyDto = new SupplyDto();
  shopId: number;
  shop: ShopDto = new ShopDto();
  supplierId: number;
  supplier: SupplierDto = new SupplierDto();

  constructor(quantity?: number, price?: number, supplyId?: number, shopId?: number, supplierId?: number,) {
    this.quantity = quantity;
    this.price = price;
    this.supplyId = supplyId;
    this.shopId = shopId;
    this.supplierId = supplierId;
  }
}
