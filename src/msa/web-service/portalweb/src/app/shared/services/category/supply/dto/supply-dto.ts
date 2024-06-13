import {SupplierDto} from '../../../supplier/dto/supplier-dto';
import {UnitDto} from '../../unit/dto/unit-dto';
import {ItemDto} from '../../item/dto/item-dto';

export class SupplyDto {
  id: number | null;
  price: number = 1;
  barcode: string;

  unitId: number;
  unit: UnitDto = new UnitDto();

  itemId: number;
  item: ItemDto = new ItemDto();

  supplierId: number;
  supplier: SupplierDto = new SupplierDto();
}
