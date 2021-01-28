import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SharedConfig } from 'app/shared/services/common/sharedConfig';
import { AuthenticationService } from '../authentication/authentication.service';
import { EventFullModel, EventWorkflow } from 'app/events/models';
import { ActionResponse } from '../common/ActionResponse';
import { CommonFunctions } from '../common/commonFunctions';
import { Club } from 'app/clubs/models/club';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private eventBaseUrl = '/event';

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  convertJdbcDatesToAngularInList(listOfEvents) {
    listOfEvents.forEach(item => {
      item.eventStartTime = CommonFunctions.getDateFromJDBCString(item.eventStartTime);
      item.eventRegistrationStartTime = CommonFunctions.getDateFromJDBCString(item.eventRegistrationStartTime);
      item.eventRegistrationEndTime = CommonFunctions.getDateFromJDBCString(item.eventRegistrationEndTime);
      item.eventEndTime = CommonFunctions.getDateFromJDBCString(item.eventEndTime);
      item.dateToShare = CommonFunctions.getDateFromJDBCString(item.dateToShare);
      item.submissionTime = CommonFunctions.getDateFromJDBCString(item.submissionTime);
      item.reviewTime = CommonFunctions.getDateFromJDBCString(item.reviewTime);
    });

  }

  getEventsForRealTime(): Observable<EventFullModel[]> {
    let request = {};
    // console.log('Initial call');
    return this.http.post<EventFullModel[]>(SharedConfig.apiUrl + this.eventBaseUrl + '/getEventsForRealTime', request);
  }

  getApprovedEventsForClub(club: Club): Observable<EventFullModel[]> {

    let request = {
      clubName: club == null ? this.authService.currentUserObject.userClub : club.clubName
    };
    // console.log('Initial call');
    return this.http.post<EventFullModel[]>(SharedConfig.apiUrl + this.eventBaseUrl + '/getApprovedEventsForClub', request);
  }

  getNewEventsSincePastDate(startDate: Date): Observable<EventFullModel[]> {
    let request = {
      dateStart: startDate,
      dateEnd: null
    };
    return this.http.post<EventFullModel[]>(SharedConfig.apiUrl + this.eventBaseUrl + '/getNewEventsSincePastDate', request);
  }

  getEventsForDateRange(startDate: Date, endDate: Date): Observable<EventFullModel[]> {
    let request = {
      startDate: startDate,
      endDate: endDate
    };
    return this.http.post<EventFullModel[]>(SharedConfig.apiUrl + this.eventBaseUrl + '/getEventsForDateRange', request);
  }

  getPendingEventsForSubmitter(clubName: string) {
    let request = {
      clubName: clubName
    };
    return this.http.post(SharedConfig.apiUrl + this.eventBaseUrl + '/getPendingEventsForSubmitter', request)
      .pipe(map(successResponse => {
        // response contiains the necessary Events
        return successResponse;
      }, () => {
        // error was encountered in getting the Events

      }));
  }

  getPendingEventsForApprover() {
    let request = {};
    return this.http.post(SharedConfig.apiUrl + this.eventBaseUrl + '/getPendingEventsForApprover', request)
      .pipe(map(successResponse => {
        // response contiains the necessary Events
        return successResponse;
      }, () => {
        // error was encountered in getting the Events

      }));
  }

  approvePendingEvent(request: EventWorkflow): Observable<ActionResponse> {
    request.reviewerUserName = this.authService.currentUserObject.userName;
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.eventBaseUrl + '/approveEvent', request)
      .pipe(map(successResponse => {
        // response contiains the necessary Events
        return successResponse;
      }, () => {
        // error was encountered in getting the Events

      }));
  }

  rejectPendingEvent(request: EventWorkflow) {
    request.reviewerUserName = this.authService.currentUserObject.userName;
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.eventBaseUrl + '/rejectEvent', request)
      .pipe(map(successResponse => {
        // response contiains the necessary Events
        return successResponse;
      }, () => {
        // error was encountered in getting the Events

      }));
  }

  updateEventWorkflow(request: EventWorkflow) {
    request.reviewerUserName = this.authService.currentUserObject.userName;
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.eventBaseUrl + '/updateEventWorkflow', request)
      .pipe(map(successResponse => {
        // response contiains the necessary Events
        return successResponse;
      }, () => {
        // error was encountered in getting the Events

      }));
  }

  addNewEvent(request): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.eventBaseUrl + '/addNewEvent', request)
      .pipe(map(successResponse => {
        // response contiains the necessary Events
        return successResponse;
      }, () => {
        // error was encountered in getting the Events

      }));
  }

  addOrUpdatePosterForEvent(bulletinPoster, eventName) {
    const request: FormData = new FormData();
    request.append('file', bulletinPoster);
    request.append('eventName', eventName);
    // console.log(request);
    return this.http.post(SharedConfig.apiUrl + this.eventBaseUrl + '/addOrUpdatePosterForEvent', request)
      .pipe(map(successResponse => {
        // response contiains the necessary Events
        return successResponse;
      }, () => {
        // error was encountered in getting the Events

      }));
  }

  getPosterForEvent(bulletinPosterLoc: string) {

    return this.http.get(SharedConfig.apiUrl + this.eventBaseUrl + '/getPosterForEvent?fileName=' + bulletinPosterLoc, { responseType: 'blob' });
  }

  getPosterForEventUrl(bulletinPosterLoc: string) {
    return SharedConfig.apiUrl + this.eventBaseUrl + '/getPosterForEvent?fileName=' + bulletinPosterLoc;
  }
}
