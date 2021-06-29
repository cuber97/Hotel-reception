import {Component, OnInit, ViewChild} from '@angular/core';
import {Customer} from '../../../models/customer';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../../http.service';
import {NgForm} from '@angular/forms';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css']
})
export class EditCustomerComponent implements OnInit {
  customerId: number;
  submitted = false;
  token: string;
  pending = false;
  customer: Customer = {};
  @ViewChild('updateCustomerForm', {static: true}) updateCustomerForm: NgForm;
  constructor(private route: ActivatedRoute,
              private http: HttpService,
              private messageService: MessageService,
              private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe( paramMap => {
      this.customerId = +paramMap.get('id');
    });
    this.getCustomer();
  }

  getCustomer() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getCustomer(this.customerId, this.token).subscribe(customer => {
      this.customer = customer;
    },
      error => {
        this.router.navigate(['../'], {relativeTo: this.route});
    });
  }

  showConfirm() {
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: 'Are you sure you want to update this customer?',
      detail: 'Confirm to proceed'
    });
  }

  onConfirm() {
    this.messageService.clear('c');
    this.submitted = true;
    if (this.updateCustomerForm.invalid) {
      this.messageService.add({severity: 'error', summary: 'Error Message', detail: 'Provide valid data'});
      return;
    }
    this.pending = true;
    this.http.updateCustomer(this.customer, this.token).subscribe((customer) => {
        this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Customer updated successfully.'});
        this.router.navigate(['../'], {relativeTo: this.route});
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
