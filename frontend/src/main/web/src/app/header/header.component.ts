import {Component, Input, OnInit} from '@angular/core';
import {SidenavService} from '../sidenav.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @Input()
  logged = false;

  constructor(private sidenavService: SidenavService,
              private router: Router) {
  }

  toggleMenu() {
    this.sidenavService.toggle();
  }

  logOut() {
    console.log(localStorage);
    localStorage.removeItem('currentUser');
    this.router.navigate(['login']);
  }
}
