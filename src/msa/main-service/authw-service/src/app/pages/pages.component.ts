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
              private roleProvider: NbRoleProvider
  ) {
    this.authService.onTokenChange().subscribe((token: NbAuthJWTToken) => {
      if (token.isValid()) {
        // this.authRoles = token['payload'].roles;//NbAuthJWTToken
        // this.authRoles = token['accessTokenPayload'].roles;//NbAuthOAuth2JWTToken
        // console.log('this.authRoles ', this.authRoles);

        this.roleProvider.getRole().subscribe((roles: string[]) => {
          this.authRoles = roles;
        })
      }
    });

    this.menu = [
      {
        title: 'Homepage',
        icon: 'home-outline',
        link: '/pages/home',
        home: true,
      },
      // {
      //   title: 'ADMINISTRATIONS',
      //   group: true,
      //   hidden: (this.authRoles.indexOf('ROLE_ADMIN')) !== -1 ? false : true,
      // },
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
          {
            title: 'Audit log',
            link: '/pages/administration/auditLog',
            hidden: this.isHidden([PrivilegeConst.AUDITLOG], this.authRoles),
          },
        ],
      },
      {
        title: 'Category',
        icon: 'grid-outline',
        hidden: this.isHidden([PrivilegeConst.COUNTRY], this.authRoles),
        children: [
          {
            title: 'Country',
            link: '/pages/category/country',
            hidden: this.isHidden([PrivilegeConst.COUNTRY], this.authRoles),
          },
          {
            title: 'Province',
            link: '/pages/category/province',
            hidden: this.isHidden([PrivilegeConst.PROVINCE], this.authRoles),
          },
          {
            title: 'District',
            link: '/pages/category/district',
            hidden: this.isHidden([PrivilegeConst.DISTRICT], this.authRoles),
          },
          {
            title: 'Unit',
            link: '/pages/category/unit',
            hidden: this.isHidden([PrivilegeConst.UNIT], this.authRoles),
          },
          {
            title: 'Group item',
            link: '/pages/category/groupItem',
            hidden: this.isHidden([PrivilegeConst.GROUPITEM], this.authRoles),
          },
          {
            title: 'Item',
            link: '/pages/category/item',
            hidden: this.isHidden([PrivilegeConst.ITEM], this.authRoles),
          },
          {
            title: 'Supply',
            link: '/pages/category/supply',
            hidden: this.isHidden([PrivilegeConst.SUPPLY], this.authRoles),
          },
          {
            title: 'Shop order',
            link: '/pages/category/shopOrder',
            hidden: this.isHidden([PrivilegeConst.SHOPORDER], this.authRoles),
          },
          {
            title: 'Chat',
            link: '/pages/category/chat',
            // hidden: this.isHidden([PrivilegeConst.CHAT], this.authRoles),
          }
        ],
      },
      {
        title: 'Shop',
        icon: 'shopping-bag-outline',
        link: '/pages/shop',
        hidden: this.isHidden([PrivilegeConst.SHOP], this.authRoles),
      },
      {
        title: 'Supplier',
        icon: 'shopping-bag-outline',
        link: '/pages/supplier',
        hidden: this.isHidden([PrivilegeConst.SUPPLIER], this.authRoles),
      },
    ];
  }

  isHidden(hasRoles: string[], authRoles: any) {
    if (!hasRoles || hasRoles.length == 0 || authRoles.indexOf(RoleConst.ROLE_ADMIN) >= 0) {
      return false;
    } else {
      return hasRoles.every((value) => authRoles.indexOf(value) >= 0 ? false : true);
    }
  }


}
