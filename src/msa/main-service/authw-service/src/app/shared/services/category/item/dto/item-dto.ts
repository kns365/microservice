import {GroupItemDto} from '../../group-item/dto/group-item-dto';
import {SupplierDto} from '../../../supplier/dto/supplier-dto';
import {UnitDto} from '../../unit/dto/unit-dto';

export class ItemDto {
  id: number | null;
  code: string;
  name: string;
  price: number = 1;

  unitId: number;
  unit: UnitDto = new UnitDto();

  groupItemId: number;
  groupItem: GroupItemDto = new GroupItemDto();

  supplierId: number;
  supplier: SupplierDto = new SupplierDto();

  // orderMethodId: number;
  // deliveryMethodId: number;
  // returnMethodId: number;
  // storageConditionId: number;

  constructor(id?: number | null, name?: string) {
    this.id = id;
    this.name = name;
  }
}
