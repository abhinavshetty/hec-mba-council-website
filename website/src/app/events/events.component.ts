import { Component, OnInit } from '@angular/core';
import { AuthenticationService, EventService } from 'app/shared/services';
import { User, UserRoles } from 'app/user/models';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { EventWorkflow } from './models';
import { ApprovedEventsTableMetadata } from './models/approvedEventsTableMetadata';

declare interface TableData {
    headerRow: string[];
    dataRows: string[][];
}

@Component({
    selector: 'app-events',
    templateUrl: './events.component.html',
    styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {
    public eventDataApproved;
    public eventDataPending;
    headerRowsFiltered;

    public workflowHeaderRow = [{ name: 'Event Name', field: 'eventName', show: true },
    { name: 'Start time', field: 'eventStartTime', show: false },
    { name: 'End time', field: 'eventEndTime', show: false },
    { name: 'Club name', field: 'host', show: false },
    { name: 'Submitting user', field: 'submitterUserName', show: false },
    { name: 'Reviewing user', field: 'reviewerUserName', show: false },
    { name: 'Review status', field: 'reviewStatus', show: false },
    { name: 'Submit time', field: 'submissionTime', show: false },
    { name: 'Review time', field: 'reviewTime', show: false },
    { name: 'Review comments', field: 'reviewComments', show: false }];
    uploadedFiles: any[] = [];
    eventsTableHeaders = [];
    approvedEventsData: [];
    pendingEventsData = [];
    chosenEvent: EventWorkflow = new EventWorkflow();
    isRowEdit = false;
    fileUploadedForPoster;
    eventPoster;

    showEventPopup = false;

    public ADD_EVENT_HEADER = 'Add a new event';
    public APPROVE_REJECT_EVENT = 'Approve/Reject event';
    public EDIT_EVENT = 'Edit event';
    popupHeader = '';

    constructor(private authService: AuthenticationService, private eventService: EventService, private messageService: MessageService) { }

    isNotNullOrUndefined(field) {
        return field != undefined && field != null;
    }

    isEventEndTimeValid(eventStartTime: Date, eventEndTime: Date) {
        return this.isNotNullOrUndefined(eventStartTime) && this.isNotNullOrUndefined(eventEndTime) && eventStartTime < eventEndTime;
    }

    myUploader(event) {
        console.log(event);
        this.fileUploadedForPoster = event.files[0];
    }

    onEventAddSuccess() {
        this.showEventPopup = false;
        // clean up resources
        this.cleanInputModels();
    }

    cleanInputModels(): void {
        this.chosenEvent = new EventWorkflow();
        this.fileUploadedForPoster = null;
        this.isRowEdit = false;
        this.eventPoster = undefined;
    }

    userSubscription: Subscription;
    currentUser: User;

    ngOnInit() {
        this.userSubscription = this.authService.getUser()
            .subscribe(user => {
                this.currentUser = user;
            });

        this.headerRowsFiltered = ApprovedEventsTableMetadata.headerRow.filter(item => (item.field != 'id' && item.show));
        this.eventService.getApprovedEventsForClub(null).subscribe(res => {
            this.eventService.convertJdbcDatesToAngularInList(res);
            this.eventDataApproved = res;
        }, error => {
            this.eventDataApproved = [];
        });

        if (this.authService.currentUserObject.userRole == UserRoles.CLUB_ACCESS) {
            // console.log('showing club data');

            this.eventService.getPendingEventsForSubmitter(this.currentUser.userName).subscribe(res => {
                console.log(res);
                this.eventService.convertJdbcDatesToAngularInList(res);
                console.log(res);
                this.eventDataPending = res;
            },
                error => {
                    this.eventDataPending = [];
                });
        } else {
            this.eventService.getPendingEventsForApprover().subscribe(res => {
                this.eventService.convertJdbcDatesToAngularInList(res);
                this.eventDataPending = res;
            },
                error => {
                    this.eventDataPending = [];
                });
        }
        this.chosenEvent = new EventWorkflow();
    }

    isUserSubmitter() {
        return this.authService.currentUserObject.userRole == UserRoles.CLUB_ACCESS;
    }

    isUserReviewer() {
        return this.authService.currentUserObject.userRole == UserRoles.ADMIN_ACCESS;
    }

    addNewEvent() {
        // console.log(this.chosenBulletin);
        if (this.fileUploadedForPoster != null) {
            this.chosenEvent.eventPosterLoc = this.fileUploadedForPoster.name;
        }
        this.eventService.addNewEvent(this.chosenEvent).subscribe(res => {
            // on successful submit, hide the overlay panel
            // op.hide();

            if (this.fileUploadedForPoster != null) {
                this.eventService.addOrUpdatePosterForEvent(this.fileUploadedForPoster, this.chosenEvent.eventName).subscribe(response => {
                    // after poster is successfully sent to back end
                    this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
                    this.onEventAddSuccess();
                }, error => {
                    // popup a notification showing the error
                    console.log(error);
                });
            } else {
                this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
                this.onEventAddSuccess();
            }
        },
            error => {
                console.log(error);
                // popup a notification showing error
            });
    }

    onApproveRejectClick(eventClicked: EventWorkflow) {
        this.showEventPopup = true;
        this.popupHeader = this.APPROVE_REJECT_EVENT;
        this.chosenEvent = eventClicked;
        this.eventPoster = this.eventService.getPosterForEventUrl(eventClicked.eventPosterLoc);
    }

    onAddButtonClick() {
        this.showEventPopup = true;
        this.popupHeader = this.ADD_EVENT_HEADER;
        this.chosenEvent.host = this.authService.currentUserObject.userClub;
        this.chosenEvent.submitterUserName = this.authService.currentUserObject.userName;
    }

    private getDateFromJDBCString(timeInput): Date {
        let dateTimeString: string = timeInput.toString();
        // console.log(dateTimeString);
        dateTimeString = dateTimeString.substr(0, dateTimeString.length - 9);
        // console.log(dateTimeString);
        return new Date(dateTimeString);
    }

    onRowEditClick(eventClicked: EventWorkflow) {
        this.isRowEdit = true;
        this.showEventPopup = true;
        this.popupHeader = this.EDIT_EVENT;
        this.chosenEvent = eventClicked;
        this.eventPoster = this.eventService.getPosterForEventUrl(eventClicked.eventPosterLoc);
    }

    onRowExpandClick(eventClicked) {
        this.eventPoster = this.eventService.getPosterForEventUrl(eventClicked.eventPosterLoc);
    }

    approveEvent() {
        this.eventService.approvePendingEvent(this.chosenEvent).subscribe(res => {
            // console.log(res);
            this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
            this.onEventAddSuccess();
        }, error => {

        });
    }

    rejectEvent() {
        this.eventService.rejectPendingEvent(this.chosenEvent).subscribe(res => {
            // console.log(res);
            this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
            this.onEventAddSuccess();
        }, error => {

        });
    }

    updateEventWorkflow() {
        if (this.fileUploadedForPoster != null) {
            this.chosenEvent.eventPosterLoc = this.fileUploadedForPoster.name;
        }
        this.eventService.updateEventWorkflow(this.chosenEvent).subscribe(res => {
            // console.log(res);
            if (this.fileUploadedForPoster != null) {
                this.eventService.addOrUpdatePosterForEvent(this.fileUploadedForPoster, this.chosenEvent.eventName).subscribe(response => {
                    // after poster is successfully sent to back end
                    this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
                    this.onEventAddSuccess();
                }, error => {
                    // popup a notification showing the error
                    console.log(error);
                });
            } else {
                this.messageService.add({ severity: 'success', summary: res.responseDetail, detail: res.responseDetail });
                this.onEventAddSuccess();
            }
        }, error => {

        });
    }
}
