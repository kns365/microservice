import {Component, OnInit} from '@angular/core';

import {MENU_ITEMS} from './pages-menu';
import {NbMenuItem} from '@nebular/theme';
import * as _ from 'lodash';
import {PrivilegeConst} from '../shared/constants/PrivilegeConst';
import {RoleConst} from '../shared/constants/RoleConst';
import {NbAuthService} from '../@theme/components/auth/services/auth.service';
import {Router} from '@angular/router';
import {NbAuthJWTToken} from '../@theme/components/auth/services/token/token';
import {NbRoleProvider} from '@nebular/security';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['pages.component.scss'],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class PagesComponent {
  authRoles: string[] = []; // authRoles = localStorage.getItem('auth_app_token') ? JSON.parse(localStorage.getItem('auth_app_token')).roles : '';
  // menu = MENU_ITEMS;
  menu = [];

  constructor(private authService: NbAuthService,
              private roleProvider: NbRoleProvider,
  ) {
    this.authService.onTokenChange().subscribe((token: NbAuthJWTToken) => {
      if (token.isValid()) {
        // this.authRoles = token['payload'].roles;//NbAuthJWTToken
        // this.authRoles = token['accessTokenPayload'].roles;//NbAuthOAuth2JWTToken
        // console.error('this.authRoles ', this.authRoles);

        this.roleProvider.getRole().subscribe((roles: string[]) => {
          this.authRoles = roles;
        });
      }
    });

    this.menu = [
      {
        title: 'Homepage',
        icon: 'home-outline',
        link: '/pages/home',
        home: true,
      },
      {
        title: 'Administration',
        icon: 'grid-outline',
        hidden: this.isHidden([PrivilegeConst.ADMINISTRATION], this.authRoles),
        children: [
          {
            title: 'User',
            link: '/pages/administration/user',
            hidden: this.isHidden([PrivilegeConst.USER], this.authRoles),
          },
          {
            title: 'Role',
            link: '/pages/administration/role',
            hidden: this.isHidden([PrivilegeConst.ROLE], this.authRoles),
          },
          {
            title: 'Privilege',
            link: '/pages/administration/privilege',
            hidden: this.isHidden([PrivilegeConst.PRIVILEGE], this.authRoles),
          },
        ],
      },
    ];
  }

  isHidden(hasRoles: string[], authRoles: any) {
    if (!hasRoles || hasRoles.length === 0 || authRoles.indexOf(RoleConst.ROLE_ADMIN) >= 0) {
      return false;
    } else {
      return hasRoles.every((value) => authRoles.indexOf(value) >= 0 ? false : true);
    }
  }

}
