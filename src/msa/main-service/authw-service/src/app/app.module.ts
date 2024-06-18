/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CoreModule} from './@core/core.module';
import {ThemeModule} from './@theme/theme.module';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {NbChatModule, NbDatepickerModule, NbDialogModule, NbGlobalLogicalPosition, NbMenuModule, NbSidebarModule, NbToastrModule, NbWindowModule,} from '@nebular/theme';
import {NB_AUTH_TOKEN_INTERCEPTOR_FILTER, NbAuthJWTInterceptor} from './@theme/components/auth/public_api'; //'@nebular/auth';
import {APP_BASE_HREF} from '@angular/common';
import {AuthGuard} from './@theme/components/auth/components/auth-guard.service';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    NbSidebarModule.forRoot(),
    NbMenuModule.forRoot(),
    NbDatepickerModule.forRoot(),
    NbDialogModule.forRoot({
      hasBackdrop: true,
      closeOnBackdropClick: false,
      closeOnEsc: true,
      hasScroll: true
    }),
    NbWindowModule.forRoot(),
    NbToastrModule.forRoot({
      position: NbGlobalLogicalPosition.BOTTOM_END,
      duration: 3000,
      // limit: 5
    }),
    NbChatModule.forRoot({
      messageGoogleMapKey: 'AIzaSyA_wNuCzia92MAmdLRzmqitRGvCF7wCZPY',
    }),
    CoreModule.forRoot(),
    ThemeModule.forRoot(),
  ],
  bootstrap: [AppComponent],
  providers: [
    AuthGuard,
    {provide: APP_BASE_HREF, useValue: '/'},
    {
      provide: NB_AUTH_TOKEN_INTERCEPTOR_FILTER, useValue: function () {
        return false;
      },
    },
    {provide: HTTP_INTERCEPTORS, useClass: NbAuthJWTInterceptor, multi: true},
  ],
})
export class AppModule {
}
