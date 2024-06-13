import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from '../../@theme/components/auth/components/auth-guard.service';
import {PrivilegeConst} from '../../shared/constants/PrivilegeConst';
import {NotFoundComponent} from '../miscellaneous/not-found/not-found.component';
import {CategoryComponent} from './category.component';
import {CountryComponent} from './country/country.component';
import {ProvinceComponent} from './province/province.component';
import {DistrictComponent} from './district/district.component';
import {GroupItemComponent} from './group-item/group-item.component';
import {ItemComponent} from './item/item.component';
import {UnitComponent} from './unit/unit.component';
import {SupplyComponent} from './supply/supply.component';
import {ChatComponent} from './chat/chat.component';
import {ShopDtOrderComponent} from '../shop/shop-dt/shop-dt-order/shop-dt-order.component';

const routes: Routes = [{
  path: '',
  component: CategoryComponent,
  children: [
    {
      path: 'country',
      component: CountryComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.COUNTRY]
      },
    },
    {
      path: 'province',
      component: ProvinceComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.PROVINCE]
      },
    },
    {
      path: 'district',
      component: DistrictComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.DISTRICT]
      },
    },
    {
      path: 'unit',
      component: UnitComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.UNIT]
      },
    },
    {
      path: 'groupItem',
      component: GroupItemComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.GROUPITEM]
      },
    },
    {
      path: 'item',
      component: ItemComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.ITEM]
      },
    },
    {
      path: 'supply',
      component: SupplyComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.SUPPLY]
      },
    },
    {
      path: 'shopOrder',
      component: ShopDtOrderComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.SHOPORDER]
      },
    },
    {
      path: 'chat',
      component: ChatComponent,
      // canActivate: [AuthGuard],
      // data: {
      //   hasRoles: [PrivilegeConst.CHAT]
      // },
    }
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoryRoutingModule {
}
