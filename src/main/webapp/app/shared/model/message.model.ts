export interface IMessage {
  id?: number;
  message?: string;
  time?: Date;
  userLogin?: string;
  userId?: number;
  chatId?: number;
}

export class Message implements IMessage {
  constructor(
    public id?: number,
    public message?: string,
    public time?: Date,
    public userLogin?: string,
    public userId?: number,
    public chatId?: number
  ) {}
}
