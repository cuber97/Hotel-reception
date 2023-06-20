import {Component, OnInit, ViewChild} from '@angular/core';
import {Account} from '../../models/account';
import {HttpService} from '../../core/http.service';
import {Router} from '@angular/router';
import {NgForm} from '@angular/forms';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  account: Account = {};
  submitted = false;
  pending = false;
  images: any[];

  @ViewChild('loginForm', {static: true}) loginForm: NgForm;

  constructor(private http: HttpService,
              private router: Router,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.images = [];
    this.images.push({source: 'assets/images/hotel1.jpg', alt: '', title: 'Hotel 1'});
    this.images.push({source: 'assets/images/hotel2.jpg', alt: '', title: 'Hotel 2'});
    this.images.push({source: 'assets/images/hotel3.jpg', alt: '', title: 'Hotel 3'});
    this.account.role = 'ADMIN';
  }

  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      this.messageService.add({severity: 'error', summary: 'Error Message', detail: 'Provide valid data'});
      return;
    }
    this.pending = true;
    this.http.login(this.account).subscribe(response => {
        if (response) {
          if (response.token) {
            localStorage.setItem('currentUser', JSON.stringify(response));
            if (response.account.role === 'WORKER' || response.account.role === 'CUSTOMER') {
              this.router.navigateByUrl('/home/customers');
            } else if (response.account.role === 'ADMIN') {
              this.router.navigateByUrl('/admin');
            }
          }
        }
      },
      (error) => {
        this.messageService.add({severity: 'error', summary: 'Error Message', detail: error.message});
        this.pending = false;
      }
      , () => {
        this.pending = false;
      },
    );
  }
}
