import { BookDto } from '@/model/bookDto'
import { BookSentenceDto } from '@/model/bookSentenceDto'

export interface BookSentenceUnion {
  bookDto: BookDto;
  sentencesDto: BookSentenceDto[];
}

export class BookSentenceUnionDto implements BookSentenceUnion {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public bookDto: BookDto,
    public sentencesDto: BookSentenceDto[]
  ) {
  }
}
