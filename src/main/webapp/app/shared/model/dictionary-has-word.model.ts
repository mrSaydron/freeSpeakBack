export interface IDictionaryHasWord {
  id?: number;
  count?: number;
  dictionaryId?: number;
  wordId?: number;
}

export class DictionaryHasWord implements IDictionaryHasWord {
  constructor(public id?: number, public count?: number, public dictionaryId?: number, public wordId?: number) {}
}
