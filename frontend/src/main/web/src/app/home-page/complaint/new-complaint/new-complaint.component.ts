import {Component, OnInit, ViewChild} from '@angular/core';
import {Room} from '../../../models/room';
import {NgForm} from '@angular/forms';
import {FileUpload} from 'primeng/fileupload';
import {HttpService} from '../../../core/http.service';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';
import {Complaint} from '../../../models/complaints';
import {Customer} from '../../../models/customer';

@Component({
  selector: 'app-new-complaint',
  templateUrl: './new-complaint.component.html',
  styleUrls: ['./new-complaint.component.css']
})
export class NewComplaintComponent implements OnInit {

  token: string;
  submitted = false;
  pending = false;
  complaint: Complaint = {};
  customer: Customer = {};
  @ViewChild('newComplaintForm', {static: true}) newComplaintForm: NgForm;

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    // TODO do zmiany
    this.http.getCustomer(4, this.token).subscribe(customer => {
      this.customer = customer;
    });
  }

  showConfirm() {
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
    this.pending = true;
    this.complaint.customer = this.customer;
    this.complaint.hasBeenRead = false;
    this.complaint.creationDate = new Date();
    this.complaint.lastUpdateDate = new Date();
    this.http.addComplaint(this.complaint, this.token).subscribe(() => {
        this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Complaint added successfully.'});
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
