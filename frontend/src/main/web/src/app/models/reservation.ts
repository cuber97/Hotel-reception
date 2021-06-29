import {Customer} from './customer';
import {Room} from './room';

export class Reservation {
  constructor(
    public reservationId?: number,
    public dateFrom?: Date,
    public dateTo?: Date,
    public currency?: string,
    public price?: number,
    public guestsInRoom?: number,
    public customer?: Customer,
    public room?: Room
  ) {  }
}
