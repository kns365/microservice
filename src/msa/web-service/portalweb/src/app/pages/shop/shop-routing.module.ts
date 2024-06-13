import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from '../../@theme/components/auth/components/auth-guard.service';
import {PrivilegeConst} from '../../shared/constants/PrivilegeConst';
import {ShopComponent} from './shop.component';
import {ShopDtComponent} from './shop-dt/shop-dt.component';
import {ShopTableComponent} from './shop-table/shop-table.component';
import {ShopDtInfoComponent} from './shop-dt/shop-dt-info/shop-dt-info.component';
import {ShopDtContractComponent} from './shop-dt/shop-dt-contract/shop-dt-contract.component';
import {ShopDtExportTableComponent} from './shop-dt/shop-dt-export/shop-dt-export-table/shop-dt-export-table.component';
import {ShopDtOrderTableComponent} from './shop-dt/shop-dt-order/shop-dt-order-table/shop-dt-order-table.component';
import {ShopDtImportTableComponent} from './shop-dt/shop-dt-import/shop-dt-import-table/shop-dt-import-table.component';
import {ShopDtImportDetailTableComponent} from './shop-dt/shop-dt-import-detail/shop-dt-import-detail-table/shop-dt-import-detail-table.component';

const routes: Routes = [{
  path: '',
  component: ShopComponent,
  children: [
    {
      path: '',
      component: ShopTableComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.SHOP]
      },
    },
    {
      path: '',
      component: ShopDtComponent,
      children: [
        {
          path: ':shopId/info',
          component: ShopDtInfoComponent,
        },
        {
          path: ':shopId/contract',
          component: ShopDtContractComponent,
        },
        {
          path: ':shopId/order',
          component: ShopDtOrderTableComponent,
        },
        {
          path: ':shopId/import',
          component: ShopDtImportTableComponent,
        },
        {
          path: ':shopId/importDetail',
          component: ShopDtImportDetailTableComponent,
        },
        {
          path: ':shopId/export',
          component: ShopDtExportTableComponent,
        }
      ],
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule {
}
