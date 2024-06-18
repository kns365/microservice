import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdministrationRoutingModule} from './administration-routing.module';
import {RoleComponent} from './role/role.component';
import {UserComponent} from './user/user.component';
import {RoleTableComponent} from './role/role-table/role-table.component';
import {RoleModalComponent} from './role/role-modal/role-modal.component';
import {DataTablesModule} from 'angular-datatables';
import {NbButtonModule, NbCardModule, NbCheckboxModule, NbDatepickerModule, NbIconModule, NbInputModule, NbRadioModule, NbSelectModule, NbSpinnerModule, NbTooltipModule} from '@nebular/theme';
import {FormsModule as ngFormsModule} from '@angular/forms';
import {UserTableComponent} from './user/user-table/user-table.component';
import {UserModalComponent} from './user/user-modal/user-modal.component';
import {AuditLogComponent} from './audit-log/audit-log.component';
import {AuditLogTableComponent} from './audit-log/audit-log-table/audit-log-table.component';
import {AuditLogModalComponent} from './audit-log/audit-log-modal/audit-log-modal.component';
import {PrivilegeComponent} from './privilege/privilege.component';
import {PrivilegeTableComponent} from './privilege/privilege-table/privilege-table.component';
import {PrivilegeModalComponent} from './privilege/privilege-modal/privilege-modal.component';
import {SharedModule} from '../../shared/shared.module';

const COMPONENTS = [
  RoleComponent,
  RoleTableComponent,
  RoleModalComponent,
  UserComponent,
  UserTableComponent,
  UserModalComponent,
  AuditLogComponent,
  AuditLogTableComponent,
  AuditLogModalComponent,
  PrivilegeComponent,
  PrivilegeModalComponent,
  PrivilegeTableComponent,
];

@NgModule({
  declarations: [
    ...COMPONENTS
  ],
  imports: [
    SharedModule,
    AdministrationRoutingModule,
  ]
})
export class AdministrationModule {
}
