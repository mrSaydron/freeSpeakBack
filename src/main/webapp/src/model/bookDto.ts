export interface Book {
  id?: number;
  title?: string;
  author?: string;
  pictureId?: string;
  pictureUrl?: string;
  know?: number;
}

export class BookDto implements Book {
  constructor (
    public id?: number,
    public title?: string,
    public author?: string,
    public pictureId?: string,
    public pictureUrl?: string,
    public know?: number
  ) {
    this.pictureId = this.pictureId || ''
    this.pictureUrl = this.pictureUrl || ''
  }
}
