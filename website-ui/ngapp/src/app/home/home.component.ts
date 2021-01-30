import { Component, OnInit } from '@angular/core';
import { BulletinService } from 'app/shared/services';
import { BulletinFullModel } from 'app/bulletin/models';

declare const $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isMobileMenu() {
    if ($(window).width() > 991) {
        return false;
    }
    return true;
}

  public bulletinBoardData: BulletinFullModel[];
  headerRow = [{ name: 'id', field: 'bulletinId', show: false }, { name: 'Title', field: 'bulletinTitle', show: true },
  { name: 'Start Time', field: 'bulletinStartTime', show: false },
  { name: 'End Time', field: 'bulletinEndTime', show: false }];
  headerRowsFiltered;
  options: any;

  constructor(private bulletinService: BulletinService) { }

  ngOnInit() {

    this.headerRowsFiltered = this.headerRow.filter(item => item.show);

    this.bulletinService.getBulletinsForRealTime().subscribe(res => {
      this.bulletinBoardData = res;
    }, () => {
      this.bulletinBoardData = [];
    });

  }

  getBulletinPoster(bulletinClicked) {
    return this.bulletinService.getPosterForBulletinUrl(bulletinClicked.bulletinPosterLoc);
  }

}
