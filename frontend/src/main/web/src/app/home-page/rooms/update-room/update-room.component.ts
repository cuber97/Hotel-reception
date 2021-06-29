import {Component, OnInit, ViewChild} from '@angular/core';
import {Room} from '../../../models/room';
import {NgForm} from '@angular/forms';
import {FileUpload} from 'primeng/fileupload';
import {HttpService} from '../../../http.service';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-update-room',
  templateUrl: './update-room.component.html',
  styleUrls: ['./update-room.component.css']
})
export class UpdateRoomComponent implements OnInit {
  roomId: number;
  token: string;
  submitted = false;
  pending = false;
  uploadedFile: any;
  room: Room = {};
  @ViewChild('updateRoomForm', {static: true}) updateRoomForm: NgForm;
  @ViewChild('fileUpload', {static: true}) fileUpload: FileUpload;

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(paramMap => {
      this.roomId = +paramMap.get('id');
    });
    this.getRoom();
  }

  getRoom() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getRoom(this.roomId, this.token).subscribe(room => {
        this.room = room;
      },
      error => {
        this.router.navigate(['../'], {relativeTo: this.route});
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
    console.log('LOOOOL');
    console.log(this.room.photo);
  }

  onConfirm() {
    this.messageService.clear('c');
    this.submitted = true;
    if (this.updateRoomForm.invalid) {
      this.messageService.add({severity: 'error', summary: 'Error Message', detail: 'Provide valid data'});
      return;
    }
    this.pending = true;
    const roomData = this.updateRoomForm.value;
    const formData = new FormData();
    formData.append('room', JSON.stringify(roomData));
    formData.append('photo', this.room.photo);

    console.log(roomData);

    this.http.updateRoom(this.roomId, formData, this.token).subscribe(() => {
        this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Room updated successfully.'});
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
