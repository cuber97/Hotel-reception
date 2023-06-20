import {Component, OnInit, ViewChild} from '@angular/core';
import {Reservation} from '../../models/reservation';
import {HttpService} from '../../core/http.service';
import {Customer} from '../../models/customer';
import {Room} from '../../models/room';
import {MessageService, SelectItem} from 'primeng/api';
import {NgForm} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  submitted = false;
  pending = false;
  reservation: Reservation = {dateFrom: new Date(), dateTo: new Date(new Date().getTime() + (24 * 60 * 60 * 1000)), currency: 'PLN', guestsInRoom: 1};
  customers: Customer[];
  customersSelect: SelectItem[];
  rooms: Room[];
  roomsSelect: SelectItem[];
  currencies = [{label: 'PLN', value: 'PLN'}, {label: 'EUR', value: 'EUR'}];
  token: string;

  public readonly Math = Math;

  @ViewChild('reservationForm', {static: true}) reservationForm: NgForm;
  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.getAllCustomers();
    this.getAllRooms();
  }

  getAllCustomers() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAllCustomers(this.token).subscribe(customers => {
      this.customers = customers;
      console.log(customers);
      this.customersSelect = this.customers.map(customer => ({
        label: customer.firstName + ' ' + customer.lastName,
        value: customer
      }));
    });
  }

  getAllRooms() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAllRooms(this.token).subscribe(rooms => {
      this.rooms = rooms;
      console.log(rooms);
      this.reservation.room = this.rooms[0];
      this.roomsSelect = this.rooms.map(room => ({
        label: room.number.toString(),
        value: room
      }));
    });
  }

  showConfirm() {
    console.log(this.reservation);
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: 'Are you sure you want to update this account?',
      detail: 'Confirm to proceed'
    });
  }

  onConfirm() {
    this.messageService.clear('c');
    this.submitted = true;
    console.log(this.reservationForm);
    if (this.reservationForm.invalid) {
      this.messageService.add({severity: 'error', summary: 'Error Message', detail: 'Provide valid data'});
      return;
    }
    this.pending = true;
    this.reservation.price = this.reservation.room.dailyRateForPerson * Math.ceil(((this.reservation.dateTo.getTime() - this.reservation.dateFrom.getTime()) / (1000 * 3600 * 24)));
    console.log(this.reservation);
    this.http.addReservation(this.reservation, this.token).subscribe(() => {
        this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Reservation created successfully.'});
        this.router.navigate(['../invoice'], {relativeTo: this.route});
      }, error => {
        this.messageService.add({severity: 'error', summary: 'Error Message', detail: error.message});
        this.pending = false;
      },
      () => {
        this.pending = false;
      });
  }

  onReject() {
    this.messageService.clear('c');
  }

}
