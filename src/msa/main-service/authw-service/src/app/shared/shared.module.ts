import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DataTablesModule} from 'angular-datatables';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  NbAccordionModule,
  NbAutocompleteModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbDatepickerModule,
  NbIconModule,
  NbInputModule,
  NbRadioModule,
  NbRouteTabsetModule,
  NbSelectModule,
  NbSpinnerModule,
  NbStepperModule,
  NbTooltipModule
} from '@nebular/theme';
import {ContactComponent} from './components/contact/contact.component';
import {CountrySelectComponent} from './components/select/country-select/country-select.component';
import {ProvinceSelectComponent} from './components/select/province-select/province-select.component';
import {DistrictSelectComponent} from './components/select/district-select/district-select.component';
import {GroupItemSelectComponent} from './components/select/group-item-select/group-item-select.component';
import {AliasSelectComponent} from './components/select/alias-select/alias-select.component';
import {ContactTypeSelectComponent} from './components/select/contact-type-select/contact-type-select.component';
import {FormatNumberDirective} from './directives/format-number.directive';
import {SupplierSelectComponent} from './components/select/supplier-select/supplier-select.component';
import {UnitSelectComponent} from './components/select/unit-select/unit-select.component';
import {SupplierAutocompleteComponent} from './components/autocomplete/supplier-autocomplete/supplier-autocomplete.component';
import {ItemSelectComponent} from './components/select/item-select/item-select.component';
import {NgxBarcode6Module} from 'ngx-barcode6';
import {ShopAutocompleteComponent} from './components/autocomplete/shop-autocomplete/shop-autocomplete.component';
import {SupplyAutocompleteComponent} from './components/autocomplete/supply-autocomplete/supply-autocomplete.component';
import {SupplySelectComponent} from './components/select/supply-select/supply-select.component';
import {ShopOrderStepComponent} from './components/step/shop-order-status-step/shop-order-step.component';
import {NbSecurityModule} from '@nebular/security';
import {ShopOrderStepSelectComponent} from './components/select/shop-order-step-select/shop-order-step-select.component';
import {EnumToArrayPipe} from './pipes/enum-to-array-pipe';
import {ShopSelectComponent} from './components/select/shop-select/shop-select.component';
import {ShopExportDetailComponent} from './components/shop-export-detail/shop-export-detail.component';
import {SupplyInputComponent} from './components/input/supply-input/supply-input.component';
import {SupplyInputDtComponent} from './components/input/supply-input-dt/supply-input-dt.component';

const PIPES = [
  // LocalizePipe
  EnumToArrayPipe
];

const DIRECTIVES = [
  FormatNumberDirective
];

const COMPONENTS = [
  ContactComponent,
  CountrySelectComponent,
  ProvinceSelectComponent,
  DistrictSelectComponent,
  GroupItemSelectComponent,
  AliasSelectComponent,
  ContactTypeSelectComponent,
  SupplierSelectComponent,
  UnitSelectComponent,
  SupplierAutocompleteComponent,
  ItemSelectComponent,
  ShopAutocompleteComponent,
  SupplyAutocompleteComponent,
  SupplySelectComponent,
  ShopSelectComponent,
  ShopOrderStepComponent,
  ShopOrderStepSelectComponent,
  ShopExportDetailComponent,
  SupplyInputComponent,
  SupplyInputDtComponent
];

const MODULES = [
  CommonModule,
  DataTablesModule,
  ngFormsModule,
  NbCardModule,
  NbIconModule,
  NbInputModule,
  NbButtonModule,
  NbCheckboxModule,
  NbRadioModule,
  NbDatepickerModule,
  NbSelectModule,
  NbDatepickerModule,
  NbTooltipModule,
  NbAutocompleteModule,
  NbSpinnerModule,
  NbRouteTabsetModule,
  NbAccordionModule,
  NgxBarcode6Module,
  NbStepperModule,
  ReactiveFormsModule,
  NbSecurityModule
];

@NgModule({
  declarations: [
    ...COMPONENTS,
    ...DIRECTIVES,
    ...PIPES,
  ],
  imports: [
    ...MODULES
  ],
  exports: [
    ...MODULES,
    ...COMPONENTS,
    ...DIRECTIVES,
    ...PIPES,
  ]
})
export class SharedModule {
}
