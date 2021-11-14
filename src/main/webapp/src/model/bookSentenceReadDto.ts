import { BookSentenceHasWordDto } from '@/model/bookSentenceHasWordDto'
import { BookDto } from '@/model/bookDto'

export interface BookSentenceRead {
  id?: number;
  book?: BookDto
  words?: BookSentenceHasWordDto[];
}

export class BookSentenceReadDto implements BookSentenceRead {
  constructor (
    public id?: number,
    public book?: BookDto,
    public words?: BookSentenceHasWordDto[]
  ) {
  }
}
