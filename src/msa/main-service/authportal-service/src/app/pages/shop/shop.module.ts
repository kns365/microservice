import {NgModule} from '@angular/core';
import {ShopTableComponent} from './shop-table/shop-table.component';
import {ShopRoutingModule} from './shop-routing.module';
import {ShopComponent} from './shop.component';
import {ShopDtComponent} from './shop-dt/shop-dt.component';
import {ShopDtInfoComponent} from './shop-dt/shop-dt-info/shop-dt-info.component';
import {ShopDtContractComponent} from './shop-dt/shop-dt-contract/shop-dt-contract.component';
import {CategoryModule} from '../category/category.module';
import {SharedModule} from '../../shared/shared.module';
import {ShopDtExportComponent} from './shop-dt/shop-dt-export/shop-dt-export.component';
import {ShopDtExportTableComponent} from './shop-dt/shop-dt-export/shop-dt-export-table/shop-dt-export-table.component';
import {ShopDtExportModalComponent} from './shop-dt/shop-dt-export/shop-dt-export-modal/shop-dt-export-modal.component';
import {ShopDtOrderComponent} from './shop-dt/shop-dt-order/shop-dt-order.component';
import {ShopDtOrderModalComponent} from './shop-dt/shop-dt-order/shop-dt-order-modal/shop-dt-order-modal.component';
import {ShopDtOrderTableComponent} from './shop-dt/shop-dt-order/shop-dt-order-table/shop-dt-order-table.component';
import {ShopOrderDetailComponent} from '../../shared/components/shop-order-detail/shop-order-detail.component';
import {ShopDtImportComponent} from './shop-dt/shop-dt-import/shop-dt-import.component';
import {ShopDtImportModalComponent} from './shop-dt/shop-dt-import/shop-dt-import-modal/shop-dt-import-modal.component';
import {ShopDtImportTableComponent} from './shop-dt/shop-dt-import/shop-dt-import-table/shop-dt-import-table.component';
import {ShopDtImportDetailModalComponent} from './shop-dt/shop-dt-import-detail/shop-dt-import-detail-modal/shop-dt-import-detail-modal.component';
import {ShopDtImportDetailComponent} from './shop-dt/shop-dt-import-detail/shop-dt-import-detail.component';
import {ShopDtImportDetailTableComponent} from './shop-dt/shop-dt-import-detail/shop-dt-import-detail-table/shop-dt-import-detail-table.component';

const COMPONENTS = [
  ShopComponent,
  ShopTableComponent,

  ShopDtComponent,
  ShopDtInfoComponent,
  ShopDtContractComponent,

  ShopDtExportComponent,
  ShopDtExportTableComponent,
  ShopDtExportModalComponent,

  ShopDtOrderComponent,
  ShopDtOrderModalComponent,
  ShopDtOrderTableComponent,

  ShopDtImportComponent,
  ShopDtImportModalComponent,
  ShopDtImportTableComponent,

  ShopDtImportDetailComponent,
  ShopDtImportDetailModalComponent,
  ShopDtImportDetailTableComponent,

  ShopOrderDetailComponent,



];

@NgModule({
  declarations: [
    ...COMPONENTS,
  ],
  imports: [
    SharedModule,
    ShopRoutingModule,
    CategoryModule
  ],
  exports: []
})
export class ShopModule {
}
