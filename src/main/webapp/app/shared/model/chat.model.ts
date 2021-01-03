import { IUser } from '@/shared/model/user.model';

export const enum chatTypeEnum {
  SUPPORT = 'SUPPORT',
  COMMON = 'COMMON',
}

export interface IChat {
  id?: number;
  chatType?: chatTypeEnum;
  title?: string;
  users?: IUser[];
}

export class Chat implements IChat {
  constructor(public id?: number, public chatType?: chatTypeEnum, public title?: string, public users?: IUser[]) {}
}
