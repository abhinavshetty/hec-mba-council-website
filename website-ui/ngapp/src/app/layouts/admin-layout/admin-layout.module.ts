import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AdminLayoutRoutes } from './admin-layout.routing';

import { HomeComponent } from '../../home/home.component';
import { UserComponent } from '../../user/user.component';
import { EventsComponent } from '../../events/events.component';
import { BulletinComponent } from '../../bulletin/bulletin.component';
import { ClubsComponent } from '../../clubs/clubs.component';
import { TableModule } from 'primeng/table';
import { DataViewModule } from 'primeng/dataview';
import { CardModule } from 'primeng/card';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { SkeletonModule } from 'primeng/skeleton';
import { TabViewModule } from 'primeng/tabview';
import { OrganizationChartModule } from 'primeng/organizationchart';

import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

import { EventsCalendarComponent } from '../../events-calendar/events-calendar.component';

import { ButtonModule } from 'primeng/button';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { CalendarModule as AliasModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { DialogModule } from 'primeng/dialog';

import { ToastModule } from 'primeng/toast';

import { RippleModule } from 'primeng/ripple';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { InputTextModule } from 'primeng/inputtext';
import { InplaceModule } from 'primeng/inplace';
import { SelectButtonModule } from 'primeng/selectbutton';
import { PanelModule } from 'primeng/panel';
import { FlatpickrModule } from 'angularx-flatpickr';
import { AccordionModule } from 'primeng/accordion';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { AutoCompleteModule } from 'primeng/autocomplete';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    AutoCompleteModule,
    ConfirmDialogModule,
    AccordionModule,
    PanelModule,
    SelectButtonModule,
    InplaceModule,
    InputTextModule,
    MessagesModule,
    MessageModule,
    DialogModule,
    ToastModule,
    RippleModule,
    InputTextareaModule,
    NgbModalModule,
    FileUploadModule,
    DropdownModule,
    AliasModule,
    FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    ReactiveFormsModule,
    TableModule,
    DataViewModule,
    CardModule,
    ScrollPanelModule,
    OverlayPanelModule,
    TabViewModule,
    SkeletonModule,
    OrganizationChartModule,
    ButtonModule
  ],
  declarations: [
    HomeComponent,
    UserComponent,
    EventsComponent,
    BulletinComponent,
    ClubsComponent,
    EventsCalendarComponent
  ]
})

export class AdminLayoutModule { }
