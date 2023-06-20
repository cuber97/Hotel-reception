import {Customer} from './customer';

export class Complaint {
  constructor(
    public creationDate?: Date,
    public lastUpdateDate?: Date,
    public hasBeenRead?: boolean,
    public content?: string,
    public customer?: Customer,
  ) {  }
}
