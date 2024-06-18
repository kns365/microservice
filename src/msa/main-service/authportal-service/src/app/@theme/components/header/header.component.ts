import {Component, OnDestroy, OnInit} from '@angular/core';
import {NbGlobalLogicalPosition, NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService, NbToastrService} from '@nebular/theme';

import {UserData} from '../../../@core/data/users';
import {LayoutService} from '../../../@core/utils';
import {filter, map, takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';
import {NbAuthJWTToken, NbAuthOAuth2JWTToken} from '../auth/services/token/token';
import {NbAuthService} from '../auth/services/auth.service';
import {NbAuthResult} from '../auth/services/auth-result';
import {environment} from '../../../../environments/environment';
import {WebSocketAPI} from '../../../shared/services/web-socket-api/web-socket-api.service';
import {NotifyService} from '../../../shared/services/app/notify/notify.service';
import {ResponseDto} from '../../../shared/models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {SupplyDto} from '../../../shared/services/category/supply/dto/supply-dto';
import {PrivilegeConst} from '../../../shared/constants/PrivilegeConst';
import {NotifyDto} from '../../../shared/services/app/notify/dto/notify-dto';
import {NotificationDto} from '../../../shared/services/app/notify/dto/notification-dto';
import * as moment from 'moment';
import {NbAccessControl, NbAclService, NbRoleProvider} from '@nebular/security';
import {RoleProvider} from '../../../@core/core.module';

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
  webSocketAPI: WebSocketAPI;
  notification: NotificationDto = new NotificationDto();
  notifiesPushed: NotifyDto[] = [];
  notifyMax: number = 10;
  notifyBellClick = false;
  notifyDurationTimeout = 10000;
  connectWebSocketConfig = `${environment.connectWebSocket}`;

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
    private notifyService: NotifyService,
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
        // console.log('this.user ', this.user);
      }
    });

    // this.userService.getUsers()
    //   .pipe(takeUntil(this.destroy$))
    //   .subscribe((users: any) => {
    //     console.log('this.user in getUsers',this.user);
    //     this.user = users.nick;
    //     console.log('this.userService.getUsers() ',this.user);
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

    this.webSocketAPI = new WebSocketAPI(null, this);
    if (this.connectWebSocketConfig == 'true') {
      this.connectWebSocket();
    }

    this.getAllNotifyByUsername(false, null, false);
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


  //notification
  connectWebSocket() {
    this.webSocketAPI._connect();
  }

  handleNotifyWebSocket(message: string) {
    this.getAllNotifyByUsername(true, null, true);
  }

  getAllNotifyByUsername(isUnique: boolean, event: any, isPushNotify: boolean) {
    if (event) {
      if (this.notifyBellClick) {
        return;
      }
      this.notifyBellClick = true;
      setTimeout(() => {
        this.notifyBellClick = false;
      }, this.notifyDurationTimeout);
    }
    if (this.user && this.user.name) {
      this.notifyService.getAllNotifyByUsername(this.user.name, this.notifyMax).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.notification = <NotificationDto>res.data;
              if (isPushNotify) {
                if (isUnique) {
                  this.pushNotifyUnique(this.notification.notifies);
                } else {
                  this.pushNotify(this.notification.notifies);
                }
              }
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.NOTIFY);
              console.log('getAllNotifyByUsername ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.NOTIFY);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    } else {
      console.log('notifyService username null');
    }
  }

  pushNotifyUnique(input: NotifyDto[]) {
    input.filter(p => !p.state).reverse().forEach(p => {
      if (this.notifiesPushed.findIndex(o => o.id == p.id) === -1) {
        this.notifiesPushed.push(p);
        this.showNotify(p.data, p.id, p.createdDate);
      }
    })
  }

  pushNotify(input: NotifyDto[]) {
    input.filter(p => !p.state).reverse().forEach(p => {
      this.notifiesPushed.push(p);
      this.showNotify(p.data, p.id, p.createdDate);
    })
  }

  showNotify(message: string, id: number, createDate: Date) {
    let timeAgo = createDate ? moment(createDate).fromNow() : '';
    let show = this.toastrService.info(message, 'Notification ' + timeAgo, {
      position: NbGlobalLogicalPosition.TOP_START,
      duration: this.notifyDurationTimeout,
      duplicatesBehaviour: 'all',
      icon: 'alert-circle-outline'
    });
    show.onClick().subscribe((action) => {
      this.notifyService.readNotifyById(id).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.getAllNotifyByUsername(true, null, false);
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.NOTIFY);
              console.log('readNotifyById ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.NOTIFY);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    });
  }


}
