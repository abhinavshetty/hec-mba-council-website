import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Club } from 'app/clubs/models/club';
import { AuthenticationService, ClubService, SharedConfig } from 'app/shared/services';
import { ActionResponse } from 'app/shared/services/common/ActionResponse';
import { UserService } from 'app/shared/services/user/user.service';
import { MessageService } from 'primeng/api';
import { map } from 'rxjs/operators';
import { Comms, User, UserRoles } from './models';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  currentUser: User;

  popupHeader: string;

  showChangePasswordPopup = false;
  oldPassword: string;
  newPassword: string;
  newPasswordConfirmation: string;
  isOldPasswordIncorrect = false;

  selectedUser: User;
  activeUsers: User[];
  showChangeUserAccessPopup = false;
  newUserRole = UserRoles.CLUB_ACCESS;
  userRoles: any[] = UserRoles.USER_ROLES;
  clubs: Club[];

  msgs = [];

  currentComms: Comms = new Comms();
  showChangeCommsPopup = false;
  constructor(private clubService: ClubService, private messageService: MessageService, private userService: UserService, private authService: AuthenticationService) {
    this.currentUser = this.authService.currentUserValue;
  }

  ngOnInit() {
  }

  startChangePassword() {
    this.showChangePasswordPopup = true;
    this.popupHeader = 'Change your password';
  }

  changePassword() {
    this.userService.modifyUserPassword(this.oldPassword, this.newPassword).subscribe(res => {
      if (res.responseMessage === 'SUCCESS') {
        // password was changed successfully!
        this.cleanDialogModels();
        this.messageService.clear();
        this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
      } else {
        this.isOldPasswordIncorrect = true;
      }
    }, error => {
      this.cleanDialogModels();
      this.messageService.clear();
      this.messageService.add({ severity: 'danger', summary: 'Unexpected error! Please refresh the page and try again.', detail: 'Unexpected error! Please refresh the page and try again.' });
    });
  }

  startChangeUserAccess() {
    this.userService.getUserList().subscribe(res => {
      this.activeUsers = res;
      this.showChangeUserAccessPopup = true;
      this.popupHeader = 'Change access roles for an existing user.';
    }, error => {

    });

    this.clubService.getAllClubs().subscribe(res => {
      this.clubs = res;
    });
  }

  changeUserAccess() {
    let request = {
      userName: this.selectedUser.userName,
      userEmail: this.selectedUser.userEmail,
      userClub: this.selectedUser.userClub === '' ? null : this.selectedUser.userClub,
      userRole: this.newUserRole,
      lastUpdate: undefined
    }
    this.userService.modifyUserAccess(request).subscribe(res => {
      this.cleanDialogModels();
      this.messageService.clear();
      this.messageService.add({severity: 'success', summary: res.responseDetail, detail: res.responseDetail});
    }, error => {
      console.log(error);
    });
  }

  startChangeCommsEmail() {
    this.userService.getEmailToAdmin().subscribe(res => {
      this.currentComms = res;
      this.showChangeCommsPopup = true;
      this.popupHeader = 'Edit periodic emails sent to admin to share links.';
    });
  }

  changeCommsEmail() {
    this.userService.editEmailToAdmin(this.currentComms).subscribe(res => {
      if (res) {
        // password was changed successfully!
        this.cleanDialogModels();
        this.messageService.clear();
        this.messageService.add({ severity: 'success', summary: 'Mail modified successfully!'});
      } else {
        // in case of failure
      }
    }, error => {
      this.cleanDialogModels();
      this.messageService.clear();
      this.messageService.add({ severity: 'danger', summary: 'Unexpected error! Please refresh the page and try again.' });
    });
  }

  cleanDialogModels() {
    this.showChangePasswordPopup = false;
    this.popupHeader = null;
    this.showChangeUserAccessPopup = false;
    this.isOldPasswordIncorrect = false;
    this.oldPassword = undefined;
    this.newPassword = undefined;
    this.newPasswordConfirmation = undefined;
    this.selectedUser = undefined;
    this.currentComms = new Comms();
    this.showChangeCommsPopup = false;
  }

  isUserAdmin(): boolean {
    return this.currentUser.userRole === UserRoles.ADMIN_ACCESS;
  }
}
