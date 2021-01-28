import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SharedConfig } from 'app/shared/services/common/sharedConfig';
import { BulletinFullModel, BulletinWorkflow } from 'app/bulletin/models';
import { AuthenticationService } from '../authentication/authentication.service';
import { ActionResponse } from '../common/ActionResponse';
import { CommonFunctions } from '../common/commonFunctions';
import { Club } from 'app/clubs/models/club';

@Injectable({
  providedIn: 'root'
})
export class BulletinService {

  private bulletinBaseUrl = '/bulletin';

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getBulletinsForRealTime(): Observable<BulletinFullModel[]> {
    let request = {};
    // console.log('Initial call');
    return this.http.post<BulletinFullModel[]>(SharedConfig.apiUrl + this.bulletinBaseUrl + '/getBulletinsForRealTime', request);
  }

  getApprovedBulletinsForClub(club: Club): Observable<BulletinFullModel[]> {
    let request = {
      clubName: club == null ? this.authService.currentUserObject.userClub : club.clubName
    };
    // console.log('Initial call');
    return this.http.post<BulletinFullModel[]>(SharedConfig.apiUrl + this.bulletinBaseUrl + '/getApprovedBulletinsForClub', request);
  }

  getNewBulletinsSincePastDate(startDate: Date): Observable<BulletinFullModel[]> {
    let request = {
      dateStart: startDate,
      dateEnd: null
    };
    return this.http.post<BulletinFullModel[]>(SharedConfig.apiUrl + this.bulletinBaseUrl + '/getNewBulletinsSincePastDate', request);
  }

  getBulletinsForDateRange(startDate: Date, endDate: Date) {
    let request = {
      dateStart: startDate,
      dateEnd: endDate
    };
    return this.http.post(SharedConfig.apiUrl + this.bulletinBaseUrl + '/getBulletinsForDateRange', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  getPendingBulletinsForSubmitter(clubName: string) {
    let request = {
      clubName: clubName
    };
    return this.http.post(SharedConfig.apiUrl + this.bulletinBaseUrl + '/getPendingBulletinsForSubmitter', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  getPendingBulletinsForApprover() {
    let request = {};
    return this.http.post(SharedConfig.apiUrl + this.bulletinBaseUrl + '/getPendingBulletinsForApprover', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  approvePendingBulletin(request: BulletinWorkflow): Observable<ActionResponse> {
    request.reviewerUserName = this.authService.currentUserObject.userName;
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.bulletinBaseUrl + '/approveBulletin', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  rejectPendingBulletin(request: BulletinWorkflow) {
    request.reviewerUserName = this.authService.currentUserObject.userName;
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.bulletinBaseUrl + '/rejectBulletin', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  updateBulletinWorkflow(request: BulletinWorkflow) {
    request.reviewerUserName = this.authService.currentUserObject.userName;
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.bulletinBaseUrl + '/updateBulletinWorkflow', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  addNewBulletin(request): Observable<ActionResponse> {
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.bulletinBaseUrl + '/addNewBulletin', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  convertJdbcDatesToAngularInList(listOfEvents) {
    listOfEvents.forEach(item => {
      item.bulletinStartTime = CommonFunctions.getDateFromJDBCString(item.bulletinStartTime);
      item.bulletinEndTime = CommonFunctions.getDateFromJDBCString(item.bulletinEndTime);
      item.submissionTime = CommonFunctions.getDateFromJDBCString(item.submissionTime);
      item.reviewTime = CommonFunctions.getDateFromJDBCString(item.reviewTime);
    });

  }

  addOrUpdatePosterForBulletin(bulletinPoster, bulletinTitle) {
    const request: FormData = new FormData();
    request.append('file', bulletinPoster);
    request.append('bulletinTitle', bulletinTitle);
    // console.log(request);
    return this.http.post(SharedConfig.apiUrl + this.bulletinBaseUrl + '/addOrUpdatePosterForBulletin', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  getPosterForBulletin(bulletinPosterLoc: string) {

    return this.http.get(SharedConfig.apiUrl + this.bulletinBaseUrl + '/getPosterForBulletin?fileName=' + bulletinPosterLoc, { responseType: 'blob' });
  }

  getPosterForBulletinUrl(bulletinPosterLoc: string) {
    return SharedConfig.apiUrl + this.bulletinBaseUrl + '/getPosterForBulletin?fileName=' + bulletinPosterLoc;
  }

}
