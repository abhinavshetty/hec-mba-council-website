import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRoles } from 'app/user/models';

import { AuthenticationService } from './authentication.service';

@Injectable({ providedIn: 'root' })
export class AdvancedAuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        // console.log('checking authguard')
        const currentUser = this.authenticationService.currentUserValue;
        if (currentUser && currentUser.userRole !== UserRoles.STUDENT_ACCESS) {
            // console.log('checking authguard' + currentUser)
            // authorised so return true
            return true;
        }

        // not logged in so redirect to login page with the return url
        this.authenticationService.logout();
        this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
        return false;
    }
}