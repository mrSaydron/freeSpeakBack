import { IUser } from '@/shared/model/user.model';

export interface IBook {
  id?: number;
  title?: string;
  author?: string;
  source?: string;
  text?: string;
  publicBook?: boolean;
  dictionaryId?: number;
  loadedUserLogin?: string;
  loadedUserId?: number;
  users?: IUser[];
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public title?: string,
    public author?: string,
    public source?: string,
    public text?: string,
    public publicBook?: boolean,
    public dictionaryId?: number,
    public loadedUserLogin?: string,
    public loadedUserId?: number,
    public users?: IUser[]
  ) {
    this.publicBook = this.publicBook || false;
  }
}
