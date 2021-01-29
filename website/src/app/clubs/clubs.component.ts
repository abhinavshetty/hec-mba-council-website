import { Component, OnInit } from '@angular/core';
import { ApprovedBulletinTableMetadata } from 'app/bulletin/models';
import { ApprovedEventsTableMetadata } from 'app/events/models/approvedEventsTableMetadata';
import { AuthenticationService, BulletinService, ClubService, EventService } from 'app/shared/services';
import { User, UserRoles } from 'app/user/models';
import { MessageService, TreeNode } from 'primeng/api';
import { Club } from './models/club';
import { ClubMetadata } from './models/clubMetadata';
import { ConfirmationService } from 'primeng/api';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-clubs',
  templateUrl: './clubs.component.html',
  styleUrls: ['./clubs.component.scss'],
  providers: [ConfirmationService]
})
export class ClubsComponent implements OnInit {
  clubs: Array<any>;
  hierarchyData: TreeNode[];
  showClubPopup = false;
  aboutTheCouncil: string;

  showAddClubPopup = false;
  fileUploaded;
  chosenClub: Club = new Club();
  uploadedFiles = [];
  popupHeader;

  sortField: string;

  chosenClubMetadata: ClubMetadata[] = [];
  mbaCouncilMetadata = [];
  newMetadata: ClubMetadata = new ClubMetadata();
  showAddMetadataPopup = false;

  eventDataForClub = [];
  bulletinDataForClub = [];

  eventHeaderRowsFiltered = [];
  bulletinHeaderRowsFiltered = [];

  private ABOUT_TAB = 0;
  private EVENTS_TAB = 1;
  private BULLETINS_TAB = 2;
  private HIERARCHY_TAB = 3;
  private RESOURCES_TAB = 4;

  resourcesDataForClub = [];
  resourcesHeader = [];

  userSubscription: Subscription;
  currentUser: User;
  msgs = [];

  // tabViewDisabled = false;
  constructor(private confirmationService: ConfirmationService, private clubService: ClubService, private bulletinService: BulletinService, private eventService: EventService,
    private authService: AuthenticationService, private messageService: MessageService) {
    this.clubService.getClubMetadata('MBA Council').subscribe(res => {
      this.mbaCouncilMetadata = res;
    });
    this.bulletinHeaderRowsFiltered = ApprovedBulletinTableMetadata.headerRow.filter(item => (item.field != 'id'));
    this.eventHeaderRowsFiltered = ApprovedEventsTableMetadata.headerRow.filter(item => (item.field != 'dateToShare' && item.field != 'id' && item.show));
    this.resourcesHeader = [{ id: 'id', field: 'clubResource' }];
  }

  isUserSubmitterForClub() {
    return this.isNotNullOrUndefined(this.currentUser) && this.currentUser.userClub == this.chosenClub.clubName && this.currentUser.userRole == UserRoles.CLUB_ACCESS;
  }

  onAddMetadataSectionClick() {
    this.showAddMetadataPopup = true;
    this.newMetadata = new ClubMetadata();
    this.newMetadata.clubName = this.authService.currentUserObject.userClub;
  }

  onDeleteMetadataSectionClick(metadataItem: ClubMetadata) {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to proceed? This operation is irreversible.',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        let request: Club = {
          clubName: this.chosenClub.clubName,
          clubResource: metadataItem.metadataName
        }
        this.clubService.deleteClubMetadata(request).subscribe(res => {
          this.messageService.add({ severity: 'info', summary: res.responseDetail, detail: res.responseDetail });
        }, error => { });
      },
      reject: () => { }
    });
  }

  addMetadataSection() {
    this.clubService.addClubMetadata(this.newMetadata).subscribe(res => {
      this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
      this.cleanInputModels();
    });
  }

  getResource(resource) {
    this.clubService.getResourceForClub(resource.clubName, resource.clubResource);
  }

  isNotNullOrUndefined(field) {
    return field != null && field != undefined;
  }

  isUserReviewer() {
    return this.isNotNullOrUndefined(this.currentUser) && this.currentUser.userRole == UserRoles.ADMIN_ACCESS;
  }

  onAddButtonClick() {
    this.showAddClubPopup = true;
    this.popupHeader = 'Add a new club';
  }

  myUploader(event) {
    console.log(event);
    this.fileUploaded = event.files[0];
  }

  addNewClub() {
    this.clubService.addClub(this.fileUploaded, this.chosenClub.clubName).subscribe(res => {
      // after poster is successfully sent to back end
      this.messageService.add({ severity: 'success', summary: res.responseDetail });
      this.onClubAddSuccess();
    }, error => {
      // popup a notification showing the error
      console.log(error);
    });
  }

  updateClub() {
    this.clubService.updateClub(this.fileUploaded, this.chosenClub.clubName).subscribe(res => {
      // after poster is successfully sent to back end
      this.messageService.add({ severity: 'success', summary: res.responseDetail });
      this.onClubAddSuccess();
    }, error => {
      // popup a notification showing the error
      console.log(error);
    });
  }

  cleanInputModels() {
    this.chosenClub = new Club();
    this.fileUploaded = null;
    this.newMetadata = new ClubMetadata();
    this.showAddClubPopup = false;
    this.showAddMetadataPopup = false;
    this.showClubPopup = false;
    this.uploadedFiles = [];
  }

  onClubAddSuccess() {
    this.showAddClubPopup = false;
    // clean up resources
    this.cleanInputModels();
  }

  onCardClick(club) {
    this.chosenClub = club;
    if (this.chosenClub.clubName == 'MBA Council') {
      this.chosenClubMetadata = this.mbaCouncilMetadata;
    } else {
      this.clubService.getClubMetadata(this.chosenClub.clubName).subscribe(res => {
        this.chosenClubMetadata = res;
      });
    }

    this.showClubPopup = true;
  }

  hideClubOverlay() {
    // this.tabViewDisabled = false;
  }

  onClubTabChange(event) {
    if (event.index == this.ABOUT_TAB) {
    }
    else if (event.index == this.EVENTS_TAB) {
      this.eventService.getApprovedEventsForClub(this.chosenClub).subscribe(res => {
        this.eventService.convertJdbcDatesToAngularInList(res);
        this.eventDataForClub = res;
      }, error => {
        this.eventDataForClub = [];
      });
    } else if (event.index == this.BULLETINS_TAB) {
      this.bulletinService.getApprovedBulletinsForClub(this.chosenClub).subscribe(res => {
        this.bulletinService.convertJdbcDatesToAngularInList(res);
        this.bulletinDataForClub = res;
      }, error => {
        this.bulletinDataForClub = [];
      });

    } else if (event.index == this.HIERARCHY_TAB) {
      this.retrieveClubHierarchy();
    } else if (event.index == this.RESOURCES_TAB) {
      this.clubService.getClubResources(this.chosenClub.clubName).subscribe(res => {
        this.resourcesDataForClub = res;
      });
    }
  }

  retrieveClubHierarchy() {
    this.hierarchyData = [{
      label: 'Root',
      expanded: true,
      children: [
        {
          label: 'Child 1',
          expanded: true,
          children: [
            {
              label: 'Grandchild 1.1', type: 'leaf'
            },
            {
              label: 'Grandchild 1.2', type: 'leaf'
            }
          ]
        },
        {
          label: 'Child 2',
          expanded: true,
          children: [
            {
              label: 'Child 2.1', type: 'leaf'
            },
            {
              label: 'Child 2.2', type: 'leaf'
            }
          ]
        }
      ]
    }];
  }

  ngOnInit() {
    this.userSubscription = this.authService.getUser()
      .subscribe(user => {
        this.currentUser = user;
      });

    this.clubService.getAllClubs().subscribe(res => {
      this.clubs = res;
    });
  }

  getLogoForClubUrl(club: Club) {
    return this.clubService.getLogoForClubUrl(club.clubResource);
  }

}
