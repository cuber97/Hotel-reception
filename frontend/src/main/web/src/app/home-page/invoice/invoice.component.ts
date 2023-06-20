import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {HttpService} from '../../core/http.service';
import {Reservation} from '../../models/reservation';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {
  token: string;
  reservations: Reservation[] = [];
  reservation: Reservation = {};
  cols = [
    { field: 'customerFirstName', header: 'Customer first name' },
    { field: 'lastName', header: 'Customer last name ' },
    { field: 'email', header: 'Customer email' },
    { field: 'dateFrom', header: 'Date from' },
    { field: 'dateTo', header: 'Date to' },
    { field: 'currency', header: 'Currency' },
    { field: 'price', header: 'Price' },
    { field: 'roomNumber', header: 'Room number' },
    { field: 'generateInvoice', header: 'Generate invoice' },
    { field: 'delete', header: 'Delete invoice' },
  ];

  constructor(private messageService: MessageService,
              private http: HttpService) {
  }

  ngOnInit() {
    this.getAllReservations();
  }

  getAllReservations() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAllReservations(this.token).subscribe(reservations => {
      this.reservations = reservations;
      console.log(reservations);
    });
  }

  onReservationDelete(reservationId: number) {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.deleteReservation(reservationId, this.token).subscribe(() => {
      this.getAllReservations();
    });
  }

  showConfirm(reservation: Reservation) {
    this.messageService.clear();
    this.reservation = reservation;
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: 'Are you sure you want to generate invoice?',
      detail: 'Confirm to proceed'
    });
  }

  onConfirm() {
    this.messageService.clear('c');
    this.http.generateInvoice(this.reservation, this.token).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Invoice was generated and sent to the customer.'});
      this.getAllReservations();
    });
  }

  onReject() {
    this.messageService.clear('c');
  }
}
