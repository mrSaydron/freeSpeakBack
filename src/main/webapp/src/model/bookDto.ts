import { User } from '@/model/userDto.js'

export interface Book {
  id?: number;
  title?: string;
  author?: string;
  source?: string;
  text?: string | null | ArrayBuffer;
  publicBook?: boolean;
  dictionaryId?: number;
  loadedUserLogin?: string;
  loadedUserId?: number;
  users?: User[];
  pictureName?: string;
  pictureUrl?: string;
  know?: number;
}

export class BookDto implements Book {
  constructor (
    public id?: number,
    public title?: string,
    public author?: string,
    public source?: string,
    public text?: string | null | ArrayBuffer,
    public publicBook?: boolean,
    public dictionaryId?: number,
    public loadedUserLogin?: string,
    public loadedUserId?: number,
    public users?: User[],
    public pictureName?: string,
    public pictureUrl?: string,
    public know?: number
  ) {
    this.publicBook = this.publicBook || false
    this.pictureName = this.pictureName || ''
    this.pictureUrl = this.pictureUrl || ''
  }
}
