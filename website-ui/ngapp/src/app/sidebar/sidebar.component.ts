import { Component, OnInit } from '@angular/core';
import { AuthenticationService, ClubService } from 'app/shared/services';
import { User } from 'app/user/models';
import { Subscription } from 'rxjs';

declare const $: any;
declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/home', title: 'Home', icon: 'pe-7s-home', class: '' },
  { path: '/user', title: 'User Profile', icon: 'pe-7s-user', class: '' },
  { path: '/events', title: 'Events', icon: 'pe-7s-note2', class: '' },
  { path: '/bulletins', title: 'Bulletins', icon: 'pe-7s-news-paper', class: '' },
  { path: '/clubs', title: 'Clubs', icon: 'pe-7s-science', class: '' }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent implements OnInit {
  menuItems: any[];
  userSubscription: Subscription;
  currentUser: User;

  constructor(private clubService: ClubService, private authService: AuthenticationService) { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
    this.userSubscription = this.authService.getUser()
      .subscribe(user => {
        this.currentUser = user;
      });
  }

  isMobileMenu() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  }

  get councilLogoUrl(): string {
    return this.clubService.getLogoForCouncilUrl();
  }

  isUserLoggedIn() {
    return this.currentUser != undefined;
  }

  isUserLoggedOut() {
    return this.currentUser == undefined;
  }
}
