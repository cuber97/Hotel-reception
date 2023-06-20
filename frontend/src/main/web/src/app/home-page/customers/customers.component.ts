import {Component, OnInit} from '@angular/core';
import {HttpService} from '../../core/http.service';
import {MessageService} from 'primeng/api';
import {Customer} from '../../models/customer';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  token: string;
  customers: Customer[] = [];
  deletedCustomerId = 0;
  cols = [
    { field: 'firstName', header: 'First Name' },
    { field: 'lastName', header: 'Last Name' },
    { field: 'email', header: 'Email' },
    { field: 'pesel', header: 'Pesel' },
    { field: 'creditCardNumber', header: 'Credit Card Number' },
    { field: 'phoneNumber', header: 'Phone number' },
    { field: 'updateCustomer', header: 'Update customer' },
    { field: 'deleteCustomer', header: 'Delete customer' },
  ];

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getAllCustomers();
  }

  getAllCustomers() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAllCustomers(this.token).subscribe(customers => {
      this.customers = customers;
    });
  }

  onCustomerDelete(customerId: number) {
    this.deletedCustomerId = customerId;
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
      summary: 'Are you sure you want to delete this customer?',
      detail: 'Confirm to proceed'
    });
  }

  onConfirm() {
    this.messageService.clear('c');
    this.http.deleteCustomer(this.deletedCustomerId, this.token).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Customer deleted successfully.'});
      this.getAllCustomers();
    });
  }

  onReject() {
    this.messageService.clear('c');
  }
}
