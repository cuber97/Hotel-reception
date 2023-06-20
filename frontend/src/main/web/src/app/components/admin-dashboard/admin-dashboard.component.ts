import {Component, OnInit} from '@angular/core';
import {HttpService} from '../../core/http.service';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-test',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  token: string;
  accounts: Account[] = [];
  deletedAccountId = 0;

  constructor(private http: HttpService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.getAllAccounts();
  }

  getAllAccounts() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAllAccounts(this.token).subscribe(accounts => {
      this.accounts = accounts;
    });
  }

  onAccountDelete(accountId: number) {
    this.deletedAccountId = accountId;
    this.showConfirm();
  }

  showConfirm() {
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: 'Are you sure you want to delete account?',
      detail: 'Confirm to proceed'
    });
  }

  onConfirm() {
    this.messageService.clear('c');
    this.http.deleteAccount(this.deletedAccountId, this.token).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Account deleted successfully.'});
      this.getAllAccounts();
    });
  }

  onReject() {
    this.messageService.clear('c');
  }
}
