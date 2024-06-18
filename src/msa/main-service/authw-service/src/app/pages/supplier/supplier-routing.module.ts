import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from '../../@theme/components/auth/components/auth-guard.service';
import {PrivilegeConst} from '../../shared/constants/PrivilegeConst';
import {SupplierComponent} from './supplier.component';
import {SupplierDtComponent} from './supplier-dt/supplier-dt.component';
import {SupplierTableComponent} from './supplier-table/supplier-table.component';
import {SupplierDtInfoComponent} from './supplier-dt/supplier-dt-info/supplier-dt-info.component';
import {SupplierDtContractComponent} from './supplier-dt/supplier-dt-contract/supplier-dt-contract.component';
import {SupplyTableComponent} from '../category/supply/supply-table/supply-table.component';
import {ShopDtOrderTableComponent} from '../shop/shop-dt/shop-dt-order/shop-dt-order-table/shop-dt-order-table.component';

const routes: Routes = [{
  path: '',
  component: SupplierComponent,
  children: [
    {
      path: '',
      component: SupplierTableComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.SUPPLIER]
      },
    },
    {
      path: '',
      component: SupplierDtComponent,
      children: [
        {
          path: ':supplierId/info',
          component: SupplierDtInfoComponent,
        },
        {
          path: ':supplierId/contract',
          component: SupplierDtContractComponent,
        },
        {
          path: ':supplierId/supply',
          component: SupplyTableComponent,
        },
        {
          path: ':supplierId/shopOrder',
          component: ShopDtOrderTableComponent,
        },
      ],
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupplierRoutingModule {
}
