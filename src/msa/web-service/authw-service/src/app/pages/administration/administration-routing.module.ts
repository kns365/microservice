import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RoleComponent} from './role/role.component';
import {UserComponent} from './user/user.component';
import {AuthGuard} from '../../@theme/components/auth/components/auth-guard.service';
import {AdministrationComponent} from './administration.component';
import {PrivilegeComponent} from './privilege/privilege.component';
import {PrivilegeConst} from '../../shared/constants/PrivilegeConst';

const routes: Routes = [{
  path: '',
  component: AdministrationComponent,
  children: [
    {
      path: 'user',
      component: UserComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.USER],
      },
    },
    {
      path: 'role',
      component: RoleComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.ROLE],
      },
    },
    {
      path: 'privilege',
      component: PrivilegeComponent,
      canActivate: [AuthGuard],
      data: {
        hasRoles: [PrivilegeConst.PRIVILEGE],
      },
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministrationRoutingModule {
}
