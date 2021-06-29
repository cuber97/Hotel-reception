import {Component, OnInit, ViewChild} from '@angular/core';
import {Complaint} from '../../../models/complaints';
import {Customer} from '../../../models/customer';
import {NgForm} from '@angular/forms';
import {HttpService} from '../../../http.service';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-read-complaint',
  templateUrl: './read-complaint.component.html',
  styleUrls: ['./read-complaint.component.css']
})
export class ReadComplaintComponent implements OnInit {

  token: string;
  submitted = false;
  pending = false;
  complaintId: number;
  complaint: Complaint = {};
  @ViewChild('newComplaintForm', {static: true}) newComplaintForm: NgForm;

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe( paramMap => {
      this.complaintId = +paramMap.get('id');
    });
    this.getComplaint();
  }

  getComplaint() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getComplaintById(this.complaintId, this.token).subscribe(complaint => {
        this.complaint = complaint;
      },
      error => {
        this.router.navigate(['../'], {relativeTo: this.route});
      });
  }
}
