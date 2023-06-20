import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {CaptchaModule} from 'primeng/captcha';
import {LoginComponent} from './components/login/login.component';
import {FormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {RegisterComponent} from './components/register/register.component';
import {RecaptchaModule} from 'ng-recaptcha';
import {HomePageComponent} from './home-page/home-page.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule, MatListModule, MatSidenavModule, MatSliderModule, MatToolbarModule, MatButtonModule} from '@angular/material';
import {AdminDashboardComponent} from './components/admin-dashboard/admin-dashboard.component';
import {HeaderComponent} from './components/header/header.component';
import {ButtonModule} from 'primeng/button';
import {SidebarModule} from 'primeng/sidebar';
import {MainNavComponent} from './components/main-nav/main-nav.component';
import {LayoutModule} from '@angular/cdk/layout';
import {HttpClientModule} from '@angular/common/http';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {TableModule} from 'primeng/table';
import {FullCalendarModule} from '@fullcalendar/angular';
import {TimetableComponent} from './home-page/timetable/timetable.component';
import {CustomersComponent} from './home-page/customers/customers.component';
import {InvoiceComponent} from './home-page/invoice/invoice.component';
import {AdminEditDashboardComponent} from './components/admin-dashboard/admin-edit-dashboard/admin-edit-dashboard.component';
import {NewCustomerComponent} from './home-page/customers/new-customer/new-customer.component';
import {EditCustomerComponent} from './home-page/customers/edit-customer/edit-customer.component';
import {ReservationComponent} from './home-page/reservation/reservation.component';
import {CalendarModule} from 'primeng/calendar';
import {DropdownModule} from 'primeng/dropdown';
import {DatePipe} from '@angular/common';
import {FileUploadModule} from 'primeng/fileupload';
import {GalleriaModule} from 'primeng/galleria';
import {NewRoomComponent} from './home-page/rooms/new-room/new-room.component';
import {RoomComponent} from './home-page/rooms/room.component';
import { UpdateRoomComponent } from './home-page/rooms/update-room/update-room.component';
import { ComplaintComponent } from './home-page/complaint/complaint.component';
import { NewComplaintComponent } from './home-page/complaint/new-complaint/new-complaint.component';
import { ReadComplaintComponent } from './home-page/complaint/read-complaint/read-complaint.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomePageComponent,
    AdminDashboardComponent,
    HeaderComponent,
    MainNavComponent,
    TimetableComponent,
    CustomersComponent,
    InvoiceComponent,
    AdminEditDashboardComponent,
    NewCustomerComponent,
    EditCustomerComponent,
    ReservationComponent,
    NewRoomComponent,
    RoomComponent,
    UpdateRoomComponent,
    ComplaintComponent,
    NewComplaintComponent,
    ReadComplaintComponent
  ],
  imports: [
    BrowserModule,
    CaptchaModule,
    RecaptchaModule,
    FormsModule,
    AppRoutingModule,
    MatSliderModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatToolbarModule,
    MatButtonModule,
    ButtonModule,
    SidebarModule,
    LayoutModule,
    HttpClientModule,
    ToastModule,
    TableModule,
    FullCalendarModule,
    CalendarModule,
    DropdownModule,
    FileUploadModule,
    GalleriaModule
  ],
  providers: [MessageService, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
