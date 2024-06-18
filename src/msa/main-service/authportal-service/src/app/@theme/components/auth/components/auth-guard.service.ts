import {Injectable, OnInit} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {NbAuthJWTToken, NbAuthService, NbTokenService} from '../public_api';

import * as _ from 'lodash';
import {NbMenuService} from '@nebular/theme';
import {RoleConst} from '../../../../shared/constants/RoleConst';
import {tap} from 'rxjs/operators';
import {NbRoleProvider} from '@nebular/security';

@Injectable()
export class AuthGuard implements CanActivate {
  authRoles: string[] = [];//roles from logged, backend

  constructor(private authService: NbAuthService,
              private router: Router,
              private nbTokenService: NbTokenService,
              private roleProvider: NbRoleProvider
  ) {
    this.authService.onTokenChange().subscribe((token: NbAuthJWTToken) => {
      if (token.isValid()) {
        // this.isAuth = true;
        // this.authRoles = token['payload'].roles;//NbAuthJWTToken
        // this.authRoles = token['accessTokenPayload'].roles;//NbAuthOAuth2JWTToken

        this.roleProvider.getRole().subscribe((roles: string[]) => {
          this.authRoles = roles;
        })
      }
    });
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.authService.isAuthenticated().pipe(tap(authenticated => {
        if (!authenticated) {
          this.nbTokenService.clear()
          this.router.navigate(['auth/login']);
          return false;
        } else {
          let hasRoles: string[] = route.data.hasRoles;//roles from required access, frontend
          if (!hasRoles || this.authRoles.indexOf(RoleConst.ROLE_ADMIN) >= 0) {//no need any role //is admin
            return true;
          }
          let authRolesTemp = this.authRoles;
          return hasRoles.every(function (role) {
            if (authRolesTemp.indexOf(role) !== -1)
              return true;
            else
              return false;
          });
        }
      }),
    );
  }
}
