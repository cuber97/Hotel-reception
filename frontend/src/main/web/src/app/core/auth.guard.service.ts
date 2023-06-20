import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (localStorage.getItem('currentUser') &&
      route.data.roles.indexOf(JSON.parse(localStorage.getItem('currentUser')).account.role !== -1)) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
