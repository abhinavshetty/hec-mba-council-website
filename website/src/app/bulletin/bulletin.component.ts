import { Component, OnInit } from '@angular/core';
import { AuthenticationService, BulletinService } from 'app/shared/services';
import { User, UserRoles } from 'app/user/models';
import { ApprovedBulletinTableMetadata, BulletinFullModel, BulletinWorkflow } from './models';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-bulletin',
  templateUrl: './bulletin.component.html',
  styleUrls: ['./bulletin.component.scss']
})
export class BulletinComponent implements OnInit {

  constructor(private bulletinService: BulletinService, private authService: AuthenticationService,
    private messageService: MessageService) { }

  uploadedFiles: any[] = [];

  public bulletinBoardDataApproved;
  public bulletinBoardDataPending;
  headerRowsFiltered;

  public workflowHeaderRow = [{ name: 'Title', field: 'bulletinTitle', show: true },
  { name: 'Start time', field: 'bulletinStartTime', show: false },
  { name: 'End time', field: 'bulletinEndTime', show: false },
  { name: 'Submitting user', field: 'submitterUserName', show: false },
  { name: 'Reviewing user', field: 'reviewerUserName', show: false },
  { name: 'Review status', field: 'reviewStatus', show: false },
  { name: 'Submit time', field: 'submissionTime', show: false },
  { name: 'Review time', field: 'reviewTime', show: false },
  { name: 'Review comments', field: 'reviewComments', show: false }];

  public ADD_BULLETIN_HEADER = 'Add a new bulletin';
  public APPROVE_REJECT_BULLETIN = 'Approve/Reject Bulletin';
  public EDIT_BULLETIN = 'Edit bulletin';

  bulletinStaging: BulletinWorkflow;
  chosenBulletin: BulletinWorkflow;

  showBulletinPopup = false;
  popupHeader = '';

  fileUploadedForPoster;
  bulletinPoster;
  msgs = [];
  isRowEdit = false;

  /**
   * Bulletin validation functions
   */
  isBulletinTitleValid(bulletinTitle: string) {
    return bulletinTitle && bulletinTitle.length != 0;
  }

  isNotNullOrUndefined(field) {
    return field != undefined && field != null;
  }

  isBulletinEndTimeValid(bulletinStartTime: Date, bulletinEndTime: Date) {
    return this.isNotNullOrUndefined(bulletinStartTime) && this.isNotNullOrUndefined(bulletinEndTime) && bulletinEndTime > bulletinStartTime;
  }

  isNewBulletinValid(inputBulletin: BulletinWorkflow) {
    let result = true;
    result = result && this.isBulletinTitleValid(inputBulletin.bulletinTitle);
    result = result && this.isBulletinEndTimeValid(inputBulletin.bulletinStartTime, inputBulletin.bulletinEndTime);
    return result;
  }

  onAddButtonClick() {
    this.showBulletinPopup = true;
    this.popupHeader = this.ADD_BULLETIN_HEADER;
    this.chosenBulletin.clubName = this.authService.currentUserObject.userClub;
    this.chosenBulletin.submitterUserName = this.authService.currentUserObject.userName;
  }

  onRowEditClick(bulletinClicked) {
    this.isRowEdit = true;
    this.showBulletinPopup = true;
    this.popupHeader = this.EDIT_BULLETIN;
    this.chosenBulletin = bulletinClicked;
    this.bulletinPoster = this.bulletinService.getPosterForBulletinUrl(bulletinClicked.bulletinPosterLoc);
  }

  onRowExpandClick(bulletinClicked) {
    this.bulletinPoster = this.bulletinService.getPosterForBulletinUrl(bulletinClicked.bulletinPosterLoc);
  }

  isUserSubmitter() {
    return this.authService.currentUserObject.userRole == UserRoles.CLUB_ACCESS;
  }

  isUserReviewer() {
    return this.authService.currentUserObject.userRole == UserRoles.ADMIN_ACCESS;
  }

  userSubscription: Subscription;
  currentUser: User;

  ngOnInit() {
    this.userSubscription = this.authService.getUser()
      .subscribe(user => {
        this.currentUser = user;
      });

    this.headerRowsFiltered = ApprovedBulletinTableMetadata.headerRow.filter(item => item.field != 'id');
    this.bulletinService.getApprovedBulletinsForClub(null).subscribe(res => {
      this.bulletinService.convertJdbcDatesToAngularInList(res);
      this.bulletinBoardDataApproved = res;
    }, error => {
      this.bulletinBoardDataApproved = [];
    });

    if (this.authService.currentUserObject.userRole == UserRoles.CLUB_ACCESS) {
      // console.log('showing club data');

      this.bulletinService.getPendingBulletinsForSubmitter(this.currentUser.userName).subscribe(res => {
        this.bulletinService.convertJdbcDatesToAngularInList(res);
        this.bulletinBoardDataPending = res;
      },
        error => {
          this.bulletinBoardDataPending = [];
        });
    } else {
      this.bulletinService.getPendingBulletinsForApprover().subscribe(res => {
        this.bulletinService.convertJdbcDatesToAngularInList(res);
        this.bulletinBoardDataPending = res;
      },
        error => {
          this.bulletinBoardDataPending = [];
        });
    }
    this.chosenBulletin = new BulletinWorkflow();
  }

  showErrorDialog = false;

  addNewBulletin() {
    if (this.isNewBulletinValid(this.chosenBulletin)) {
      // execute the add function
      this.chosenBulletin.submitterUserName = this.authService.currentUserObject.userName;
      if (this.fileUploadedForPoster != null) {
        this.chosenBulletin.bulletinPosterLoc = this.fileUploadedForPoster.name;
      }
      this.bulletinService.addNewBulletin(this.chosenBulletin).subscribe(res => {
        // on successful submit, hide the overlay panel
        // op.hide();

        if (this.fileUploadedForPoster != null) {
          this.bulletinService.addOrUpdatePosterForBulletin(this.fileUploadedForPoster, this.chosenBulletin.bulletinTitle).subscribe(response => {
            // after poster is successfully sent to back end
            this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
            this.onBulletinAddSuccess();
          }, error => {
            // popup a notification showing the error
            console.log(error);
          });
        } else {
          this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
          this.onBulletinAddSuccess();
        }
      },
        error => {
          console.log(error);
          // popup a notification showing error
        });
    } else {
      // popup a dialog to tell user to fix errors.
      this.showErrorDialog = true;
    }

  }

  onBulletinAddSuccess() {
    this.showBulletinPopup = false;
    // clean up resources
    this.cleanInputModels();
  }

  cleanInputModels(): void {
    this.chosenBulletin = new BulletinWorkflow();
    this.fileUploadedForPoster = null;
    this.isRowEdit = false;
    this.bulletinPoster = undefined;
  }



  myUploader(event) {
    console.log(event);
    this.fileUploadedForPoster = event.files[0];
  }


  onApproveRejectClick(bulletinClicked: BulletinWorkflow) {
    this.showBulletinPopup = true;
    this.popupHeader = this.APPROVE_REJECT_BULLETIN;
    this.chosenBulletin = bulletinClicked;
    this.bulletinPoster = this.bulletinService.getPosterForBulletinUrl(bulletinClicked.bulletinPosterLoc);
  }

  approveBulletin() {
    this.bulletinService.approvePendingBulletin(this.chosenBulletin).subscribe(res => {
      // console.log(res);
      this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
      this.onBulletinAddSuccess();
    }, error => {

    });
  }

  rejectBulletin() {
    this.bulletinService.rejectPendingBulletin(this.chosenBulletin).subscribe(res => {
      // console.log(res);
      this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
      this.onBulletinAddSuccess();
    }, error => {

    });
  }

  updateBulletinWorkflow() {
    if (this.fileUploadedForPoster != null) {
      this.chosenBulletin.bulletinPosterLoc = this.fileUploadedForPoster.name;
    }
    this.bulletinService.updateBulletinWorkflow(this.chosenBulletin).subscribe(res => {
      // console.log(res);
      if (this.fileUploadedForPoster != null) {
        this.bulletinService.addOrUpdatePosterForBulletin(this.fileUploadedForPoster, this.chosenBulletin.bulletinTitle).subscribe(response => {
          // after poster is successfully sent to back end
          this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
          this.onBulletinAddSuccess();
        }, error => {
          // popup a notification showing the error
          console.log(error);
        });
      } else {
        this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
        this.onBulletinAddSuccess();
      }
    }, error => {

    });
  }

}
