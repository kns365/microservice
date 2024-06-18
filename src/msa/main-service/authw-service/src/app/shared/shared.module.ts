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
  NbTooltipModule,
} from '@nebular/theme';
import {FormatNumberDirective} from './directives/format-number.directive';
import {NgxBarcode6Module} from 'ngx-barcode6';
import {NbSecurityModule} from '@nebular/security';
import {EnumToArrayPipe} from './pipes/enum-to-array-pipe';

const PIPES = [
  // LocalizePipe
  EnumToArrayPipe,
];

const DIRECTIVES = [
  FormatNumberDirective,
];

const COMPONENTS = [
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
  NbSecurityModule,
];

@NgModule({
  declarations: [
    ...COMPONENTS,
    ...DIRECTIVES,
    ...PIPES,
  ],
  imports: [
    ...MODULES,
  ],
  exports: [
    ...MODULES,
    ...COMPONENTS,
    ...DIRECTIVES,
    ...PIPES,
  ],
})
export class SharedModule {
}
