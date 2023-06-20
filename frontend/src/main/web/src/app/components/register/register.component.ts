import {Component, OnInit, ViewChild} from '@angular/core';
import {Account} from '../../models/account';
import {HttpService} from '../../core/http.service';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {Captcha} from 'primeng/captcha';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  account: Account = {};
  recaptchaVerified = false;
  submitted = false;
  pending = false;

  @ViewChild('registrationForm', {static: true}) registrationForm: NgForm;
  @ViewChild('recaptcha', {static: true}) recaptcha: Captcha;

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit() {
    this.account.enabled = true;
  }

  onSubmit() {
    this.submitted = true;
    if (this.registrationForm.invalid || !this.recaptchaVerified) {
      this.messageService.add({severity: 'error', summary: 'Error Message', detail: 'Provide valid data'});
      return;
    }
    this.pending = true;
    this.http.addAccount(this.account).subscribe(() => {
        this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'You have been successfully registered'});
        this.router.navigateByUrl('/login');
      }, error => {
        this.messageService.add({severity: 'error', summary: 'Error Message', detail: error.message});
        this.pending = false;
      },
      () => {
        this.pending = false;
      });
  }
}
