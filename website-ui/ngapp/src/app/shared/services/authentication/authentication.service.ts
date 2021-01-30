import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { User, UserRoles } from 'app/user/models';
import { SharedConfig } from '../common/sharedConfig';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  retrieveSessionToken() {

  }

  getUser(): Observable<User> {
    return this.currentUserSubject.asObservable();
  }

  users = [{ userEmail: 'association.mbacouncil@hec.edu', userName: 'President', userRole: UserRoles.ADMIN_ACCESS, userClub: 'MBA Council', password: 'Council@123#' },
  { userEmail: 'abhinav.shetty@hec.edu', userName: 'Wee Pee', userRole: UserRoles.CLUB_ACCESS, userClub: 'MBA Council', password: 'Henchman@123#' }];

  login(userEmail, password) {
    let request = {
      userEmail: userEmail,
      userPassword: password
    };
    return this.http.post<any>(SharedConfig.apiUrl + '/user/authenticateUserCredentials', request)
      .pipe(map(user => {
        if (user.userEmail === null || user.userEmail === undefined) {
          // not correct credentials
        } else {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }

        return user;
      }));
  }

  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }
}