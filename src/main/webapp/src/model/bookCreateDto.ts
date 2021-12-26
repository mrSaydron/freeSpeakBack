import { TextTag } from '@/model/textTagDto'

export interface BookCreate {
  title?: string;
  author?: string;
  text?: string | null | ArrayBuffer;
  pictureId?: string;
  pictureUrl?: string;
  textTags?: TextTag[];
}

export class BookCreateDto implements BookCreate {
  constructor (
    public title?: string,
    public author?: string,
    public text?: string | null | ArrayBuffer,
    public pictureId?: string,
    public pictureUrl?: string,
    public textTags?: TextTag[]
  ) {
    this.pictureId = this.pictureId || ''
    this.pictureUrl = this.pictureUrl || ''
    this.textTags = this.textTags || []
  }
}
