import { Component, OnInit, ElementRef } from '@angular/core';
import { ROUTES } from '../../sidebar/sidebar.component';
import {Location, LocationStrategy, PathLocationStrategy} from '@angular/common';
import { AuthenticationService } from '../services';

@Component({
    // moduleId: module.id,
    selector: 'navbar-cmp',
    templateUrl: 'navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})

export class NavbarComponent implements OnInit{
    private listTitles: any[];
    location: Location;
    private toggleButton: any;
    private sidebarVisible: boolean;
    loginPopupVisible = false;
    loginModuleVisible = false;
    registerModuleVisible = false;
    constructor(location: Location,  private element: ElementRef, private authService: AuthenticationService) {
      this.location = location;
          this.sidebarVisible = false;
    }

    loginEmail: string;
    loginPassword: string;
    loginError = false;
    authenticateUser() {
        let result = this.authService.login(this.loginEmail, this.loginPassword);
        if (result) {
            // login success!
            this.loginPopupVisible = false;
        } else {
            // login error
            this.loginError = true;
        }
    }

    logout() {
        this.authService.logout();
    }

    openLoginPopup() {
        this.loginPopupVisible = true;
        this.loginModuleVisible = true;
        this.registerModuleVisible = false;
    }

    showRegisterForm() {
        this.loginModuleVisible = false;
        this.registerModuleVisible = true;
        console.log(this.registerModuleVisible)
    }

    cleanInputModels() {
        this.loginPopupVisible = false;
    }

    isUserLoggedIn() {
        return this.authService.currentUserObject != undefined;
    }

    isUserLoggedOut() {
        return this.authService.currentUserObject == undefined;
    }

    ngOnInit(){
      this.listTitles = ROUTES.filter(listTitle => listTitle);
      const navbar: HTMLElement = this.element.nativeElement;
      this.toggleButton = navbar.getElementsByClassName('navbar-toggle')[0];
    }
    sidebarOpen() {
        const toggleButton = this.toggleButton;
        const body = document.getElementsByTagName('body')[0];
        setTimeout(function(){
            toggleButton.classList.add('toggled');
        }, 500);
        body.classList.add('nav-open');

        this.sidebarVisible = true;
    };
    sidebarClose() {
        const body = document.getElementsByTagName('body')[0];
        this.toggleButton.classList.remove('toggled');
        this.sidebarVisible = false;
        body.classList.remove('nav-open');
    };
    sidebarToggle() {
        // const toggleButton = this.toggleButton;
        // const body = document.getElementsByTagName('body')[0];
        if (this.sidebarVisible === false) {
            this.sidebarOpen();
        } else {
            this.sidebarClose();
        }
    };

    getTitle(){
      var titlee = this.location.prepareExternalUrl(this.location.path());
      if(titlee.charAt(0) === '#'){
          titlee = titlee.slice( 1 );
      }

      for(var item = 0; item < this.listTitles.length; item++){
          if(this.listTitles[item].path === titlee){
              return this.listTitles[item].title;
          }
      }
      return 'Dashboard';
    }
}
