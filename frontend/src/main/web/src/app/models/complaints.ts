import {Customer} from './customer';
import {Room} from './room';

export class Complaint {
  constructor(
    public complaintId?: number,
    public creationDate?: Date,
    public lastUpdateDate?: Date,
    public hasBeenRead?: boolean,
    public content?: string,
    public customer?: Customer,
  ) {  }
}
