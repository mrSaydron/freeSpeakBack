export interface BookCreate {
  title?: string;
  author?: string;
  text?: string | null | ArrayBuffer;
  pictureId?: string;
  pictureUrl?: string;
}

export class BookCreateDto implements BookCreate {
  constructor (
    public title?: string,
    public author?: string,
    public text?: string | null | ArrayBuffer,
    public pictureId?: string,
    public pictureUrl?: string
  ) {
    this.pictureId = this.pictureId || ''
    this.pictureUrl = this.pictureUrl || ''
  }
}
