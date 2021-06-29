import {Component, OnInit, ViewChild} from '@angular/core';
import {MessageService} from 'primeng/api';
import {Room} from '../../../models/room';
import {NgForm} from '@angular/forms';
import {HttpService} from '../../../http.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FileUpload} from 'primeng/fileupload';

@Component({
  selector: 'app-new-room',
  templateUrl: './new-room.component.html',
  styleUrls: ['./new-room.component.css']
})
export class NewRoomComponent implements OnInit {
  token: string;
  submitted = false;
  pending = false;
  uploadedFile: any;
  room: Room = {};
  photos = [];
  @ViewChild('roomForm', {static: true}) roomForm: NgForm;
  @ViewChild('fileUpload', {static: true}) fileUpload: FileUpload;

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getRoomPhotos();
  }

  getRoomPhotos() {
    this.http.getRoomPhotos(this.token).subscribe(response => {
      this.photos = response;
      console.log(response);
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

  onUploadFile(event) {
    this.uploadedFile = event.files[0];
    console.log(this.uploadedFile);
    this.room.photo = this.uploadedFile;
    this.messageService.add({severity: 'success', summary: 'Success message', detail: 'File was uploaded successfully'});
    this.fileUpload.remove(event, 0);
  }

  onConfirm() {
    this.messageService.clear('c');
    this.messageService.add({severity: 'error', summary: 'Błąd', detail: 'Pokój o takim numerze już istnieje!'});
    // this.messageService.clear('c');
    // this.submitted = true;
    // if (this.roomForm.invalid) {
    //   this.messageService.add({severity: 'error', summary: 'Error Message', detail: 'Provide valid data'});
    //   return;
    // }
    // this.pending = true;
    // const roomData = this.roomForm.value;
    // const formData = new FormData();
    // formData.append('room', JSON.stringify(roomData));
    // formData.append('photo', this.room.photo);
    //
    // console.log(roomData);
    //
    // this.http.addRoom(formData, this.token).subscribe(() => {
    //     this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Room added successfully.'});
    //     this.pending = false;
    //   }, error => {
    //     this.messageService.add({severity: 'error', summary: 'Error Message', detail: error.message});
    //     this.pending = false;
    //   },
    //   () => {
    //     this.pending = false;
    //   });
  }

  onReject() {
    this.messageService.clear('c');
  }

}
