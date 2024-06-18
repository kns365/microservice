import {environment} from './../../environments/environment';
import {HttpRequest} from '@angular/common/http';
import {Injectable, ModuleWithProviders, NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NbAuthJWTToken, NbAuthModule, NbAuthOAuth2JWTToken, NbAuthService, NbPasswordAuthStrategy} from '../@theme/components/auth/public_api';//'@nebular/auth';
import {NbSecurityModule, NbRoleProvider} from '@nebular/security';
import {Observable, of as observableOf} from 'rxjs';

import {throwIfAlreadyLoaded} from './module-import-guard';
import {
  AnalyticsService,
  LayoutService,
  PlayerService,
  SeoService,
  StateService,
} from './utils';
import {UserData} from './data/users';
import {ElectricityData} from './data/electricity';
import {SmartTableData} from './data/smart-table';
import {UserActivityData} from './data/user-activity';
import {OrdersChartData} from './data/orders-chart';
import {ProfitChartData} from './data/profit-chart';
import {TrafficListData} from './data/traffic-list';
import {EarningData} from './data/earning';
import {OrdersProfitChartData} from './data/orders-profit-chart';
import {TrafficBarData} from './data/traffic-bar';
import {ProfitBarAnimationChartData} from './data/profit-bar-animation-chart';
import {TemperatureHumidityData} from './data/temperature-humidity';
import {SolarData} from './data/solar';
import {TrafficChartData} from './data/traffic-chart';
import {StatsBarData} from './data/stats-bar';
import {CountryOrderData} from './data/country-order';
import {StatsProgressBarData} from './data/stats-progress-bar';
import {VisitorsAnalyticsData} from './data/visitors-analytics';
import {SecurityCamerasData} from './data/security-cameras';

import {UserService} from './mock/users.service';
import {ElectricityService} from './mock/electricity.service';
import {SmartTableService} from './mock/smart-table.service';
import {UserActivityService} from './mock/user-activity.service';
import {OrdersChartService} from './mock/orders-chart.service';
import {ProfitChartService} from './mock/profit-chart.service';
import {TrafficListService} from './mock/traffic-list.service';
import {EarningService} from './mock/earning.service';
import {OrdersProfitChartService} from './mock/orders-profit-chart.service';
import {TrafficBarService} from './mock/traffic-bar.service';
import {ProfitBarAnimationChartService} from './mock/profit-bar-animation-chart.service';
import {TemperatureHumidityService} from './mock/temperature-humidity.service';
import {SolarService} from './mock/solar.service';
import {TrafficChartService} from './mock/traffic-chart.service';
import {StatsBarService} from './mock/stats-bar.service';
import {CountryOrderService} from './mock/country-order.service';
import {StatsProgressBarService} from './mock/stats-progress-bar.service';
import {VisitorsAnalyticsService} from './mock/visitors-analytics.service';
import {SecurityCamerasService} from './mock/security-cameras.service';
import {MockDataModule} from './mock/mock-data.module';
import {map} from 'rxjs/operators';

const socialLinks = [
  {
    url: 'https://github.com/akveo/nebular',
    target: '_blank',
    icon: 'github',
  },
  {
    url: 'https://www.facebook.com/akveo/',
    target: '_blank',
    icon: 'facebook',
  },
  {
    url: 'https://twitter.com/akveo_inc',
    target: '_blank',
    icon: 'twitter',
  },
  {
    url: 'https://svc.shinhanfinance.com.vn/',
    target: '_blank',
    icon: 'layers-outline',
  },
];

const DATA_SERVICES = [
  {provide: UserData, useClass: UserService},
  {provide: ElectricityData, useClass: ElectricityService},
  {provide: SmartTableData, useClass: SmartTableService},
  {provide: UserActivityData, useClass: UserActivityService},
  {provide: OrdersChartData, useClass: OrdersChartService},
  {provide: ProfitChartData, useClass: ProfitChartService},
  {provide: TrafficListData, useClass: TrafficListService},
  {provide: EarningData, useClass: EarningService},
  {provide: OrdersProfitChartData, useClass: OrdersProfitChartService},
  {provide: TrafficBarData, useClass: TrafficBarService},
  {provide: ProfitBarAnimationChartData, useClass: ProfitBarAnimationChartService},
  {provide: TemperatureHumidityData, useClass: TemperatureHumidityService},
  {provide: SolarData, useClass: SolarService},
  {provide: TrafficChartData, useClass: TrafficChartService},
  {provide: StatsBarData, useClass: StatsBarService},
  {provide: CountryOrderData, useClass: CountryOrderService},
  {provide: StatsProgressBarData, useClass: StatsProgressBarService},
  {provide: VisitorsAnalyticsData, useClass: VisitorsAnalyticsService},
  {provide: SecurityCamerasData, useClass: SecurityCamerasService},
];

export class NbSimpleRoleProvider extends NbRoleProvider {
  getRole() {
    // here you could provide any role based on any auth flow
    return observableOf('guest');
    // return observableOf(['guest', 'user', 'editor', 'admin']);
  }
}

@Injectable()
export class RoleProvider extends NbRoleProvider {
  constructor(private authService: NbAuthService) {
    super();
  }

  getRole(): Observable<string> {
    return this.authService.onTokenChange()
      .pipe(
        map((token: NbAuthJWTToken) => {
          // return token.isValid() ? token['payload'].roles : 'anonymous';
          return token.isValid() ? token['payload'].authorities : 'anonymous';
        }),
      );
  }
}

export const NB_CORE_PROVIDERS = [
  ...MockDataModule.forRoot().providers,
  ...DATA_SERVICES,
  ...NbAuthModule.forRoot({
//https://akveo.github.io/nebular/docs/auth/nbpasswordauthstrategy#nbpasswordauthstrategy
    strategies: [
      // NbDummyAuthStrategy.setup({
      //   name: 'email',
      //   delay: 3000,
      // }),
      NbPasswordAuthStrategy.setup({
        name: 'email',
        baseEndpoint: environment.SERVER_URL,
        login: {
          endpoint: '/auth/getToken',
          method: 'post',
          redirect: {
            success: '/pages',
            failure: null,
          },
          requireValidToken: true,
        },
        logout: {
          endpoint: '/auth/clearToken',
          method: 'get',
          redirect: {
            success: '/auth/login',
            failure: null,
          },
          requireValidToken: false,
        },
        token: {
          class: NbAuthJWTToken,
          // key: 'token', //field name token of response
          key: 'data.accessToken', //field name token of response
        },
        // token: {
        //   class: NbAuthOAuth2JWTToken,
        //   getter: function (method: any, response: any) {
        //     return {
        //       access_token: response.body.token,
        //       refresh_token: response.body.refreshToken,
        //     };
        //   },
        // },
        // refreshToken: {
        //   endpoint: '/auth/refreshToken',
        //   method: "post",
        //   redirect: {
        //     success: null,
        //     failure: null,
        //   },
        //   requireValidToken: false,
        //   defaultErrors: ['Something went wrong, please try again.'],
        //   defaultMessages: ['Your token has been successfully refreshed.'],
        // },
        // requestPass:false,
        // resetPass:{
        //   endpoint:"/auth/reset",
        //   redirect:{
        //     success:"/",
        //     failure:"/auth/login"
        //   }
        // },
      }),
    ],
    forms: {
      login: {
        // redirectDelay: 500, // delay before redirect after a successful login, while success message is shown to the user
        // strategy: 'email',  // strategy id key.
        // rememberMe: true,   // whether to show or not the `rememberMe` checkbox
        // showMessages: {     // show/not show success/error messages
        //   success: true,
        //   error: true,
        // },
        socialLinks: socialLinks,
      },
      register: {
        // redirectDelay: 500,
        // strategy: 'email',
        // showMessages: {
        //   success: true,
        //   error: true,
        // },
        // terms: true,
        socialLinks: socialLinks,
      },
    },
  }).providers,

  NbSecurityModule.forRoot({accessControl: {}}).providers,

  // NbSecurityModule.forRoot({
  //   accessControl: {
  //     ROLE_USER: {
  //       view: ['ROLE_USER_SUPPLIER'],
  //       create: '*',
  //       edit: '*',
  //       remove: '*',
  //     },
  //     ROLE_ADMIN: {
  //       parent: 'ROLE_USER',
  //       view: '*',
  //       create: '*',
  //       edit: '*',
  //       remove: '*',
  //     }
  //   },
  // }).providers,

  {
    // provide: NbRoleProvider, useClass: NbSimpleRoleProvider,
    provide: NbRoleProvider, useClass: RoleProvider,
  },
  AnalyticsService,
  LayoutService,
  PlayerService,
  SeoService,
  StateService,
];

@NgModule({
  imports: [
    CommonModule,
  ],
  exports: [
    NbAuthModule,
  ],
  declarations: [],
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }

  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        ...NB_CORE_PROVIDERS,
      ],
    };
  }
}
