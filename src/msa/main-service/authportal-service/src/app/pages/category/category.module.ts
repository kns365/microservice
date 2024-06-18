import {NgModule} from '@angular/core';

import {CategoryRoutingModule} from './category-routing.module';
import {CountryComponent} from './country/country.component';
import {CountryTableComponent} from './country/country-table/country-table.component';
import {CountryModalComponent} from './country/country-modal/country-modal.component';
import {ProvinceComponent} from './province/province.component';
import {ProvinceTableComponent} from './province/province-table/province-table.component';
import {ProvinceModalComponent} from './province/province-modal/province-modal.component';
import {DistrictComponent} from './district/district.component';
import {DistrictTableComponent} from './district/district-table/district-table.component';
import {DistrictModalComponent} from './district/district-modal/district-modal.component';
import {GroupItemComponent} from './group-item/group-item.component';
import {GroupItemTableComponent} from './group-item/group-item-table/group-item-table.component';
import {GroupItemModalComponent} from './group-item/group-item-modal/group-item-modal.component';
import {SharedModule} from '../../shared/shared.module';
import {ItemComponent} from './item/item.component';
import {ItemModalComponent} from './item/item-modal/item-modal.component';
import {ItemTableComponent} from './item/item-table/item-table.component';
import {UnitComponent} from './unit/unit.component';
import {UnitModalComponent} from './unit/unit-modal/unit-modal.component';
import {UnitTableComponent} from './unit/unit-table/unit-table.component';
import {SupplyComponent} from './supply/supply.component';
import {SupplyModalComponent} from './supply/supply-modal/supply-modal.component';
import {SupplyTableComponent} from './supply/supply-table/supply-table.component';
import {ChatComponent} from './chat/chat.component';

const COMPONENTS = [
  CountryComponent,
  CountryTableComponent,
  CountryModalComponent,
  ProvinceComponent,
  ProvinceTableComponent,
  ProvinceModalComponent,
  DistrictComponent,
  DistrictTableComponent,
  DistrictModalComponent,
  GroupItemComponent,
  GroupItemTableComponent,
  GroupItemModalComponent,
  ItemComponent,
  ItemModalComponent,
  ItemTableComponent,
  UnitComponent,
  UnitModalComponent,
  UnitTableComponent,

  SupplyComponent,
  SupplyModalComponent,
  SupplyTableComponent,
  ChatComponent,

];

@NgModule({
  declarations: [
    ...COMPONENTS
  ],
  imports: [
    SharedModule,
    CategoryRoutingModule,
  ],
  exports: []
})
export class CategoryModule {
}
