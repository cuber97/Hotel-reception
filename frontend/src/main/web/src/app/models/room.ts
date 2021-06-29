export class Room {
  constructor(
    public roomId?: number,
    // tslint:disable-next-line:variable-name
    public number?: number,
    public floor?: number,
    public maxPeopleCapacity?: number,
    public dailyRateForPerson?: number,
    public photo?: any,
    public photoFileName?: string
  ) {  }
}
