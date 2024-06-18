import {NgModule} from '@angular/core';

import {AdministrationRoutingModule} from './administration-routing.module';
import {RoleComponent} from './role/role.component';
import {UserComponent} from './user/user.component';
import {RoleTableComponent} from './role/role-table/role-table.component';
import {RoleModalComponent} from './role/role-modal/role-modal.component';
import {UserTableComponent} from './user/user-table/user-table.component';
import {UserModalComponent} from './user/user-modal/user-modal.component';
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
