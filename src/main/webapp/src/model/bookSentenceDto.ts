import {BookSentenceHasWordDto} from '@/model/BookSentenceHasWordDto';

export interface BookSentence {
  id?: number;
  words?: BookSentenceHasWordDto[];
}

export class BookSentenceDto implements BookSentence {
  constructor (
    public id?: number,
    public words?: BookSentenceHasWordDto[]
  ) {
  }
}
