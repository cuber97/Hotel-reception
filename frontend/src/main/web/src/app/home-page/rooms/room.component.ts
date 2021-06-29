import { Component, OnInit } from '@angular/core';
import {Customer} from '../../models/customer';
import {HttpService} from '../../http.service';
import {MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';
import {Room} from '../../models/room';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  token: string;
  rooms: Room[] = [];
  deletedRoomId = 0;
  photos = [];
  roomPhotosMap = new Map();
  cols = [
    { field: 'number', header: 'Number' },
    { field: 'floor', header: 'Floor' },
    { field: 'maxPeopleCapacity', header: 'Max people capacity' },
    { field: 'dailyRateForPerson', header: 'Daily rate for person' },
    { field: 'photo', header: 'Photo' },
    { field: 'updateCustomer', header: 'Update customer' },
    { field: 'deleteCustomer', header: 'Delete customer' }
  ];

  constructor(private http: HttpService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getAllRooms();
  }

  getAllRooms() {
    this.token = JSON.parse(localStorage.getItem('currentUser')).token;
    this.http.getAllRooms(this.token).subscribe(rooms => {
      this.rooms = rooms;
      this.http.getRoomPhotos(this.token).subscribe(response => {
        this.photos = response;
        this.rooms.forEach(room => {
          const roomPhoto = this.photos.find((photo) => {
            return photo.photoName === room.photoFileName;
          });
          this.roomPhotosMap.set(room.roomId, roomPhoto);
        });
        console.log(this.roomPhotosMap);
      });
    });
  }

  onRoomDelete(roomId: number) {
    this.deletedRoomId = roomId;
    this.showConfirm();
  }

  onRoomAdd() {
    this.router.navigate(['add'], {relativeTo: this.route});
  }

  showConfirm() {
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: 'Are you sure you want to delete this room?',
      detail: 'Confirm to proceed'
    });
  }

  onConfirm() {
    this.messageService.clear('c');
    this.http.deleteRoom(this.deletedRoomId, this.token).subscribe(() => {
      this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Room deleted successfully.'});
      this.getAllRooms();
    });
  }

  onReject() {
    this.messageService.clear('c');
  }

}
