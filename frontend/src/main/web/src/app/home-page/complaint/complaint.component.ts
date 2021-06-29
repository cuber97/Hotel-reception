import { Component, OnInit } from '@angular/core';
import {HttpService} from '../../http.service';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';
import {Complaint} from '../../models/complaints';

@Component({
  selector: 'app-complaint',
  templateUrl: './complaint.component.html',
  styleUrls: ['./complaint.component.css']
})
export class ComplaintComponent implements OnInit {

  token: string;
  currentUser ;
  complaints: Complaint[] = [];
  complaintId: number;
  deletedComplaintId = 0;
  customercols = [
    { field: 'creationDate', header: 'Creation date' },
    { field: 'lastUpdateDate', header: 'Last update date' },
    { field: 'updateComplaint', header: 'Update complaint' },
    { field: 'deleteComplaint', header: 'Delete complaint' },
  ];

  workerCols = [
    { field: 'creationDate', header: 'Creation date' },
    { field: 'lastUpdateDate', header: 'Last update date' },
    { field: 'customerName', header: 'Customer name' },
    { field: 'customerSurname', header: 'Customer surname' },
    { field: 'customerEmail', header: 'Customer email' },
    { field: 'read', header: 'Read' },
  ];

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getComplaintsForCustomer();
  }

  getComplaintsForCustomer() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.currentUser = JSON.parse(localStorage.getItem('currentUser')).account;
    this.complaintId = 4
    this.http.getComplaintsForCustomer(4, this.token).subscribe(complaints => {
      this.complaints = complaints;
    });
  }

  onComplaintDelete(complaintId: number) {
    this.deletedComplaintId = complaintId;
    this.showConfirm();
  }

  onCustomerAdd() {
    this.router.navigate(['add'], {relativeTo: this.route});
  }

  showConfirm() {
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: 'Are you sure you want to delete complaint?',
      detail: 'Confirm to proceed'
    });
  }

  onConfirm() {
    this.messageService.clear('c');
    this.http.deleteComplaint(this.deletedComplaintId, this.token).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Complaint deleted successfully.'});
      this.getComplaintsForCustomer();
    });
  }

  onReject() {
    this.messageService.clear('c');
  }

}
