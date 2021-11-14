import { BookSentenceHasWordDto } from '@/model/bookSentenceHasWordDto'

export interface BookSentence {
  id?: number;
  words?: BookSentenceHasWordDto[];
}

export class BookSentenceDto implements BookSentence {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public id?: number,
    public words?: BookSentenceHasWordDto[]
  ) {
  }
}
