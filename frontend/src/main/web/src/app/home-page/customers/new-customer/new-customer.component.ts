import {Component, OnInit, ViewChild} from '@angular/core';
import {Customer} from '../../../models/customer';
import {NgForm} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../../core/http.service';
@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrls: ['./new-customer.component.css']
})
export class NewCustomerComponent implements OnInit {
  submitted = false;
  token: string;
  pending = false;
  customer: Customer = {};
  @ViewChild('newCustomerForm', {static: true}) newCustomerForm: NgForm;

  constructor(private http: HttpService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
  }

  onSubmit() {
    console.log(this.customer);
    this.submitted = true;
    if (this.newCustomerForm.invalid) {
      return;
    }
    this.pending = true;
    this.http.addCustomer(this.customer, this.token).subscribe(() => {
        this.router.navigate(['../'], {relativeTo: this.route});
      }, error => {
        this.pending = false;
      },
      () => {
        this.pending = false;
      });
  }
}
