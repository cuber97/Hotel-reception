import {Component, OnInit, ViewChild} from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import {Reservation} from '../../models/reservation';
import {HttpService} from '../../core/http.service';
import {DatePipe} from '@angular/common';
import {NgForm} from '@angular/forms';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent implements OnInit {
  calendarPlugins = [dayGridPlugin];
  reservations: Reservation[] = [];
  token: string;
  @ViewChild('fullCalendarComponent', {static: true}) fullCalendarComponent: NgForm;
  calendarEvents = [
  ];
  calendarEventsTmp = [];

  constructor(private http: HttpService,
              private datePipe: DatePipe) {
  }

  ngOnInit() {
    this.getAllReservations();
  }

  prepareEvents(reservations: Reservation[]) {
    Object.assign( this.calendarEventsTmp, this.calendarEvents);
    reservations.forEach(reservation => {
      console.log(reservation);
      this.calendarEventsTmp.push({
        title: 'Room ' + reservation.room.number + ' occupied',
        date: this.datePipe.transform(reservation.dateFrom.toString(), 'yyyy-MM-dd'),
        extraParams: reservation
      });
      console.log(this.datePipe.transform(reservation.dateFrom.toString(), 'yyyy-MM-dd'));
      this.calendarEvents = this.calendarEventsTmp;
      this.calendarEventsTmp.push({
        title: 'Room ' + reservation.room.number + ' free',
        date: this.datePipe.transform(reservation.dateTo.toString(), 'yyyy-MM-dd'),
        extraParams: reservation
      });
      console.log(this.datePipe.transform(reservation.dateTo.toString(), 'yyyy-MM-dd'));
    });
    console.log(this.calendarEvents);
    this.calendarEvents = this.calendarEventsTmp;
  }

  getAllReservations() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAllReservations(this.token).subscribe(reservations => {
      this.reservations = reservations;
      console.log(reservations);
      this.prepareEvents(reservations);
    });
  }

  handleDateClick(event) { // handler method
    console.log(event);
    alert(event);
  }
}
