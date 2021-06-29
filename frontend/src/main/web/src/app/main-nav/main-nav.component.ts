import {Component, OnInit, ViewChild} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {SidenavService} from '../sidenav.service';
import {MatSidenav} from '@angular/material';

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent implements OnInit {
  opened = true;
  role: string;

  @ViewChild('sidenav', {static: true}) public sidenav: MatSidenav;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver, private sidenavService: SidenavService) {
  }

  ngOnInit(): void {
    this.role = JSON.parse(localStorage.getItem('currentUser')).account.role;
    this.sidenavService.setSidenav(this.sidenav);
  }
}
