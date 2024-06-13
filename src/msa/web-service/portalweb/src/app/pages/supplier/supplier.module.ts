import {NgModule} from '@angular/core';
import {SupplierTableComponent} from './supplier-table/supplier-table.component';
import {SupplierRoutingModule} from './supplier-routing.module';
import {SupplierComponent} from './supplier.component';
import {SupplierDtComponent} from './supplier-dt/supplier-dt.component';
import {SupplierDtInfoComponent} from './supplier-dt/supplier-dt-info/supplier-dt-info.component';
import {SupplierDtContractComponent} from './supplier-dt/supplier-dt-contract/supplier-dt-contract.component';
import {CategoryModule} from '../category/category.module';
import {SharedModule} from '../../shared/shared.module';


const COMPONENTS = [
  SupplierComponent,
  SupplierTableComponent,
  SupplierDtComponent,
  SupplierDtInfoComponent,
  SupplierDtContractComponent,
];

@NgModule({
  declarations: [
    ...COMPONENTS,
  ],
  imports: [
    SharedModule,
    SupplierRoutingModule,
    CategoryModule
  ],
  exports: [
    ...COMPONENTS
  ]
})
export class SupplierModule {
}
