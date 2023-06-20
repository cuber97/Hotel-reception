export class Account {
  constructor(
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public password?: string,
    public enabled?: boolean,
    public role?: string,
    public phoneNumber?: string,
  ) {  }
}
