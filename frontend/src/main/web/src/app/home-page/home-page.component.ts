import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http.service';
import dayGridPlugin from '@fullcalendar/daygrid';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {
  public loginUser: any = {};
  public users: any = [];
  calendarPlugins = [dayGridPlugin];

  calendarEvents = [
    { title: 'event 1', date: '2019-04-01' }
  ];

  constructor(private http: HttpService) {
    this.loginUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  // ngOnInit() {
  //   this.http.getAllAccounts(this.loginUser.token).subscribe(users => {
  //     this.users = users;
  //     console.log(this.users);
  //   });
  // }
}
