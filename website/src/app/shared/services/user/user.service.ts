import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Comms, UserRoles } from 'app/user/models';
import { User } from 'app/user/models/user';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../authentication/authentication.service';
import { ActionResponse } from '../common/ActionResponse';
import { SharedConfig } from '../common/sharedConfig';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userApiUrl = '/user'

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  register(user: User): Observable<ActionResponse> {
    user.userRole = UserRoles.STUDENT_ACCESS;
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.userApiUrl + '/registerNewUser', user);
  }

  getUserList() {
    // gets the user list for the MBA council reviewer. He uses this to modify access for a given user.
    return this.http.get<User[]>(SharedConfig.apiUrl + this.userApiUrl + '/getAllUsers');
  }

  modifyUserPassword(oldPassword, newPassword) {
    let request = {
      userEmail: this.authService.currentUserValue.userEmail,
      userPassword: newPassword,
      oldUserPassword: oldPassword
    }
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.userApiUrl + '/modifyUserPassword', request);
  }

  deleteUser() {

  }

  modifyUserAccess(user: User) {
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.userApiUrl + '/modifyUserProfile', user);
  }

  getEmailToAdmin() {
    return this.http.get<Comms>(SharedConfig.apiUrl + '/comms/getEmailToShareToAdmin');
  }

  editEmailToAdmin(comms: Comms) {
    return this.http.post<Boolean>(SharedConfig.apiUrl + '/comms/editEmailToShareToAdmin', comms);
  }
}
