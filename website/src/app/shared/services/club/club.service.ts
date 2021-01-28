import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Club } from 'app/clubs/models/club';
import { ClubMetadata } from 'app/clubs/models/clubMetadata';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActionResponse } from '../common/ActionResponse';
import { SharedConfig } from '../common/sharedConfig';
import { saveAs } from "file-saver";

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  updateClub(clubLogo: any, clubName: string) {
    const request: FormData = new FormData();
    request.append('file', clubLogo);
    request.append('clubName', clubName);
    // console.log(request);
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.clubBaseUrl + '/updateClub', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  private clubBaseUrl = "/club";

  constructor(private http: HttpClient) { }

  /** 
   *  gets the data to show in the icon and name for the list
   */
  getAllClubs(): Observable<Club[]> {
    return this.http.get<any[]>(SharedConfig.apiUrl + this.clubBaseUrl + '/getAllClubs');
  }

  getLogoForClubUrl(clubResource: string) {
    return SharedConfig.apiUrl + this.clubBaseUrl + '/getLogoForClub?fileName=' + clubResource;
  }

  getLogoForCouncilUrl() {
    return SharedConfig.apiUrl + this.clubBaseUrl + '/getLogoForCouncil';
  }

  addClub(clubLogo, clubName) {
    const request: FormData = new FormData();
    request.append('file', clubLogo);
    request.append('clubName', clubName);
    // console.log(request);
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.clubBaseUrl + '/addClub', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  getClubResources(clubName: string) {
    let request = {
      clubName: clubName
    }
    return this.http.post<Club[]>(SharedConfig.apiUrl + this.clubBaseUrl + '/getClubResources', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  addResourceForClub(clubName: string, clubResourceFile) {
    const request: FormData = new FormData();
    request.append('file', clubResourceFile);
    request.append('clubName', clubName);
    // console.log(request);
    return this.http.post(SharedConfig.apiUrl + this.clubBaseUrl + '/addResourceForClub', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  deleteResourceForClub(clubName: string, clubResource: string) {
    let request = {
      clubName: clubName,
      clubResource: clubResource
    }
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.clubBaseUrl + '/deleteResourceForClub', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  getResourceForClub(clubName: string, clubResource: string) {
    let request = {
      clubName: clubName,
      clubResource: clubResource
    }
    return this.http.post(SharedConfig.apiUrl + this.clubBaseUrl + '/getResourceForClub', request, {
      responseType: "blob",
      headers: new HttpHeaders().append("Content-Type", "application/json")})
      .subscribe(res => {
        saveAs(res, clubResource);
      });
  }

  getClubMetadata(clubName: string) {
    let request = {
      clubName: clubName
    }
    return this.http.post<ClubMetadata[]>(SharedConfig.apiUrl + this.clubBaseUrl + '/getClubMetadata', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  addClubMetadata(request: ClubMetadata) {
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.clubBaseUrl + '/addClubMetadata', request)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  editClubMetadata(clubMetadata: ClubMetadata) {
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.clubBaseUrl + '/editClubMetadata', clubMetadata)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }

  deleteClubMetadata(club: Club) {
    return this.http.post<ActionResponse>(SharedConfig.apiUrl + this.clubBaseUrl + '/deleteClubMetadata', club)
      .pipe(map(successResponse => {
        // response contiains the necessary bulletins
        return successResponse;
      }, () => {
        // error was encountered in getting the bulletins

      }));
  }
}
