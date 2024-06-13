// import { Injectable } from '@angular/core';
//
// import {
//     CanActivate, Router,
//     ActivatedRouteSnapshot,
//     RouterStateSnapshot,
//     CanActivateChild
// } from '@angular/router';
//
// @Injectable()
// export class AppRouteGuard implements CanActivate, CanActivateChild {
//
//     constructor(
//     ) { }
//
//     canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
//       // localStorage.getItem()
//
//         if (!this.sessionService.user) {
//             this.router.navigate(['/account/login']);
//             return false;
//         }
//
//         if (!route.data || !route.data['permission']) {
//             return true;
//         }
//
//         if (this.permissionChecker.isGranted(route.data['permission'])) {
//             return true;
//         }
//
//         this.router.navigate([this.selectBestRoute()]);
//         return false;
//     }
//
//     canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
//         return this.canActivate(route, state);
//     }
//
//     selectBestRoute(): string {
//         if (!this.sessionService.user) {
//             return '/account/login';
//         }
//
//         if (this.permissionChecker.isGranted('Pages.Users')) {
//             return '/app/admin/users';
//         }
//
//         return '/app/home';
//     }
// }
