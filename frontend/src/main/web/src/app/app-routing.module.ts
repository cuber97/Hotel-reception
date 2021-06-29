import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HomePageComponent} from './home-page/home-page.component';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';
import {TimetableComponent} from './home-page/timetable/timetable.component';
import {CustomersComponent} from './home-page/customers/customers.component';
import {InvoiceComponent} from './home-page/invoice/invoice.component';
import {AdminEditDashboardComponent} from './admin-dashboard/admin-edit-dashboard/admin-edit-dashboard.component';
import {NewCustomerComponent} from './home-page/customers/new-customer/new-customer.component';
import {EditCustomerComponent} from './home-page/customers/edit-customer/edit-customer.component';
import {ReservationComponent} from './home-page/reservation/reservation.component';
import {AuthGuard} from './auth.guard';
import {NewRoomComponent} from './home-page/rooms/new-room/new-room.component';
import {RoomComponent} from './home-page/rooms/room.component';
import {UpdateRoomComponent} from './home-page/rooms/update-room/update-room.component';
import {ComplaintComponent} from './home-page/complaint/complaint.component';
import {NewComplaintComponent} from './home-page/complaint/new-complaint/new-complaint.component';
import {ReadComplaintComponent} from './home-page/complaint/read-complaint/read-complaint.component';


const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'home',
    component: HomePageComponent,
    canActivate: [AuthGuard],
    data: {roles: ['WORKER', 'CUSTOMER']},
    children: [
      {
        path: '',
        redirectTo: 'customers',
        pathMatch: 'full'
      },
      {
        path: 'timetable',
        component: TimetableComponent,
      },
      {
        path: 'invoice',
        component: InvoiceComponent,
      },
      {
        path: 'rooms',
        children: [
          {
            path: '',
            component: RoomComponent,
          },
          {
            path: 'add',
            component: NewRoomComponent,
          },
          {
            path: ':id',
            component: UpdateRoomComponent,
          },
        ]
      },
      {
        path: 'reservation',
        component: ReservationComponent,
      },
      {
        path: 'customers',
        children: [
          {
            path: '',
            component: CustomersComponent,
          },
          {
            path: 'add',
            component: NewCustomerComponent,
          },
          {
            path: ':id',
            component: EditCustomerComponent,
          },
        ]
      },
      {
        path: 'complaint',
        children: [
          {
            path: '',
            component: ComplaintComponent,
          },
          {
            path: 'add',
            component: NewComplaintComponent,
          },
          {
            path: ':id',
            component: ReadComplaintComponent,
          },
        ]
      },
    ]
  },
  {
    path: 'admin',
    // canActivate: [AuthGuard],
    data: {roles: ['ADMIN']},
    children: [
      {
        path: '',
        component: AdminDashboardComponent,
      },
      {
        path: ':id',
        component: AdminEditDashboardComponent,
      },
    ]
  },
  {
    path: '**', pathMatch: 'full', redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
