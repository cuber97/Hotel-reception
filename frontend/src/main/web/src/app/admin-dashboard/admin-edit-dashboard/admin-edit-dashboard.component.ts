import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpService} from '../../http.service';
import {Account} from '../../models/account';
import {MessageService} from 'primeng/api';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-admin-edit-dashboard',
  templateUrl: './admin-edit-dashboard.component.html',
  styleUrls: ['./admin-edit-dashboard.component.css']
})
export class AdminEditDashboardComponent implements OnInit {
  account: Account = {};
  accountId: number;
  token: string;
  submitted = false;
  pending = false;
  @ViewChild('updateAccountForm', {static: true}) updateAccountForm: NgForm;

  constructor(private route: ActivatedRoute,
              private http: HttpService,
              private messageService: MessageService,
              private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe( paramMap => {
      this.accountId = +paramMap.get('id');
    });
    this.getAccount();
  }

  getAccount() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAccount(this.accountId, this.token).subscribe(account => {
      this.account = account;
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
    console.log(this.updateAccountForm);
    this.messageService.clear('c');
    this.submitted = true;
    if (this.updateAccountForm.invalid) {
      this.messageService.add({severity: 'error', summary: 'Error Message', detail: 'Provide valid data'});
      return;
    }
    this.pending = true;
    this.http.updateAccount(this.account, this.token).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Account updated successfully.'});
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
