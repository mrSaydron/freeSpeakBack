export interface IBook {
  id?: number;
  title?: string;
  author?: string;
  source?: string;
  text?: string;
  publicBook?: boolean;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public title?: string,
    public author?: string,
    public source?: string,
    public text?: string,
    public publicBook?: boolean
  ) {
    this.publicBook = this.publicBook || false;
  }
}
