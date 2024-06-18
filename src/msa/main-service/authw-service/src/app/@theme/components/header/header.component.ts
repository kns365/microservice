import {Component, OnDestroy, OnInit} from '@angular/core';
import {NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService, NbToastrService} from '@nebular/theme';

import {UserData} from '../../../@core/data/users';
import {LayoutService} from '../../../@core/utils';
import {map, takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';
import {NbAuthJWTToken} from '../auth/services/token/token';
import {NbAuthService} from '../auth/services/auth.service';
import {environment} from '../../../../environments/environment';
import {NbAccessControl, NbAclService, NbRoleProvider} from '@nebular/security';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})

export class HeaderComponent implements OnInit, OnDestroy {
  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  user: any;
  baseUrl = `${environment.API_ENDPOINT}`;

  themes = [
    {
      value: 'default',
      name: 'Light',
    },
    // {
    //   value: 'dark',
    //   name: 'Dark',
    // },
    // {
    //   value: 'cosmic',
    //   name: 'Cosmic',
    // },
    // {
    //   value: 'corporate',
    //   name: 'Corporate',
    // },
  ];

  currentTheme = 'default';

  userMenu = [
    {title: 'Profile', icon: 'people-outline'},
    {title: 'Settings', icon: 'settings-outline'},
    {
      title: 'Log out',
      icon: 'log-out-outline',
      link: '/auth/logout',
    },
  ];

  constructor(
    private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private userService: UserData,
    private layoutService: LayoutService,
    private breakpointService: NbMediaBreakpointsService,
    private authService: NbAuthService,
    private toastrService: NbToastrService,
    private aclService: NbAclService,
    private roleProvider: NbRoleProvider
  ) {

  }

  ngOnInit() {
    this.currentTheme = this.themeService.currentTheme;

    this.authService.onTokenChange().subscribe((token: NbAuthJWTToken) => {
      if (token.isValid()) {
        // this.user = token.getPayload(); // here we receive a payload from the token and assigns it to our user variable
        this.user = {
          name: token['payload'].sub, //token['accessTokenPayload'].sub,
          picture: 'assets/images/nick.png',
          // roles: token['payload'].roles, //token['accessTokenPayload'].roles,
          // token: token.getValue()
        };
        this.roleProvider.getRole().subscribe((roles: string[]) => {
          let permissions: NbAccessControl = {
            ROLE_USER: {
              view: roles
            },
            ROLE_ADMIN: {
              parent: 'ROLE_USER',
              view: '*',
              create: '*',
              edit: '*',
              remove: '*',
            }
          };
          this.aclService.setAccessControl(permissions);
        })
        // console.error('this.user ', this.user);
      }
    });

    // this.userService.getUsers()
    //   .pipe(takeUntil(this.destroy$))
    //   .subscribe((users: any) => {
    //     console.error('this.user in getUsers',this.user);
    //     this.user = users.nick;
    //     console.error('this.userService.getUsers() ',this.user);
    //   });

    const {xl} = this.breakpointService.getBreakpointsMap();
    this.themeService
      .onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe(
        (isLessThanXl: boolean) => (this.userPictureOnly = isLessThanXl),
      );

    this.themeService
      .onThemeChange()
      .pipe(
        map(({name}) => name),
        takeUntil(this.destroy$),
      )
      .subscribe((themeName) => (this.currentTheme = themeName));

  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(themeName: string) {
    this.themeService.changeTheme(themeName);
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }

  goToApi() {
    window.open(this.baseUrl + '/swagger', "_blank");
  }


}
