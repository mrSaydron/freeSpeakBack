import { WordDto } from '@/model/wordDto'

export interface DictionaryWord {
  id?: number,
  count?: number,
  word?: WordDto,
}

export class DictionaryWordDto implements DictionaryWord {
  constructor(
    public id?: number,
    public count?: number,
    public word?: WordDto,
  ) {
  }
}
