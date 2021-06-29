import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Account} from './models/account';
import {catchError} from 'rxjs/operators';
import {Customer} from './models/customer';
import {Reservation} from './models/reservation';
import {Complaint} from './models/complaints';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  basicUrl = 'http://localhost:8080';
  // httpOptions = {
  //   headers: new HttpHeaders({'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'})
  // };

  constructor(private http: HttpClient) {
  }

  getSampleData(): Observable<any> {
    return this.http.get('resource');
  }

  addAccount(account: Account): Observable<Account> {
    const headers = new HttpHeaders({'Access-Control-Allow-Origin': '*'});
    return this.http.post(`${this.basicUrl}/registration`, account, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  login(account: Account): Observable<any> {
    const headers = new HttpHeaders({'Access-Control-Allow-Origin': '*'});
    return this.http.post(`${this.basicUrl}/login`, account, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getAllAccounts(token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/users`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getAccount(accountId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/users/${accountId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  updateAccount(account: Account, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.put(`${this.basicUrl}/users`, account, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  deleteAccount(accountId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.delete(`${this.basicUrl}/users/${accountId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  addCustomer(customer: Customer, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.post(`${this.basicUrl}/customers`, customer, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getAllCustomers(token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/customers`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getCustomer(customerId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/customers/${customerId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  updateCustomer(customer: Customer, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.put(`${this.basicUrl}/customers`, customer, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  deleteCustomer(customerId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.delete(`${this.basicUrl}/customers/${customerId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getAllRooms(token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/rooms`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getRoom(roomId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/rooms/${roomId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  addRoom(formData: FormData, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.post(`${this.basicUrl}/rooms`, formData, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  updateRoom(roomId: number, formData: FormData, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.put(`${this.basicUrl}/rooms/${roomId}`, formData, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  deleteRoom(roomId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.delete(`${this.basicUrl}/rooms/${roomId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getAllReservations(token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/reservations`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  addReservation(reservation: Reservation, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.post(`${this.basicUrl}/reservations`, reservation, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  deleteReservation(reservationId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.delete(`${this.basicUrl}/reservation/${reservationId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  generateInvoice(reservation: Reservation, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.post(`${this.basicUrl}/invoice`, reservation, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getRoomPhotos(token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/rooms/photos`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  addComplaint(complaint: Complaint, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.post(`${this.basicUrl}/complaints`, complaint, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getComplaints(token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/complaints`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getComplaintById(complaintId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/complaints/${complaintId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  getComplaintsForCustomer(customerId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.get(`${this.basicUrl}/complaints/customer/${customerId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  deleteComplaint(complaintId: number, token: string): Observable<any> {
    const headers = new HttpHeaders({Authorization: 'Bearer ' + token});
    return this.http.delete(`${this.basicUrl}/complaints/${complaintId}`, {headers}).pipe(
      catchError(this.errorHandler)
    );
  }

  errorHandler(httpErrorResponse: HttpErrorResponse) {
    return throwError(httpErrorResponse.error);
  }
}
