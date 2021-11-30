export interface Book {
  id?: number;
  title?: string;
  author?: string;
  pictureId?: string;
  pictureUrl?: string;
  know?: number;
  isReading?: boolean;
}

export class BookDto implements Book {
  constructor (
    public id?: number,
    public title?: string,
    public author?: string,
    public pictureId?: string,
    public pictureUrl?: string,
    public know?: number,
    public isReading?: boolean
  ) {
    this.pictureId = this.pictureId || ''
    this.pictureUrl = this.pictureUrl || ''
  }
}
