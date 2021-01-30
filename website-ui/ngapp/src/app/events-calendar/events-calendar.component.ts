import { Component, ChangeDetectionStrategy, ViewChild, TemplateRef, OnInit } from '@angular/core';
import { endOfDay, endOfMonth, endOfWeek, format, isSameDay, isSameMonth, startOfDay, startOfMonth, startOfWeek } from 'date-fns';
import { Observable, Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CalendarEvent, CalendarEventAction, CalendarView } from 'angular-calendar';
import { CommonFunctions, EventService } from 'app/shared/services';
import { EventFullModel } from 'app/events/models';
import { map } from 'rxjs/operators';

const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

@Component({
  selector: 'mwl-demo-component',
  changeDetection: ChangeDetectionStrategy.Default,
  styleUrls: ['events-calendar.component.scss'],
  templateUrl: 'events-calendar.component.html',
})
export class EventsCalendarComponent implements OnInit {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;

  viewDate: Date = new Date();

  events: CalendarEvent<{ event: EventFullModel }>[];

  activeDayIsOpen: boolean = false;

  view: CalendarView = CalendarView.Month;
  showEventDialog = false;

  CalendarView = CalendarView;
  chosenEvent: EventFullModel;

  modalData: {
    action: string;
    event: CalendarEvent;
  };

  refresh: Subject<any> = new Subject();

  eventsData: EventFullModel[] = [];

  constructor(private modal: NgbModal, private eventService: EventService) {

  }

  ngOnInit(): void {
    this.fetchEvents();
  }

  fetchEvents(): void {
    const getStart: any = {
      month: startOfMonth,
      week: startOfWeek,
      day: startOfDay,
    }[this.view];

    const getEnd: any = {
      month: endOfMonth,
      week: endOfWeek,
      day: endOfDay,
    }[this.view];

    this.eventService.getEventsForDateRange(new Date(format(getStart(this.viewDate), 'yyyy-MM-dd HH:mm:ss')), new Date(format(getEnd(this.viewDate), 'yyyy-MM-dd HH:mm:ss')))
      .subscribe(res => {
        this.eventService.convertJdbcDatesToAngularInList(res);
        this.eventsData = res;
        this.transcribeEventsToDiary();
      });
  }

  transcribeEventsToDiary() {
    this.events = [];
    this.eventsData.forEach(item => {
      this.events.push({
        title: item.eventName,
        start: item.eventStartTime,
        end: item.eventEndTime,
        color: colors.yellow
      });
    });
  }

  getPosterUrl(chosenEvent) {
    return this.eventService.getPosterForEventUrl(chosenEvent.eventPosterLoc);
  }

  isNotNullOrUndefined(field) {
    return field != undefined && field != null;
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  handleEvent(event: CalendarEvent): void {
    this.chosenEvent = this.eventsData.filter(item => item.eventName == event.title)[0];
    this.showEventDialog = true;
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }
}
