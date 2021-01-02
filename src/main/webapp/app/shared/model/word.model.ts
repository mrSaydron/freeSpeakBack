export interface IWord {
  id?: number;
  word?: string;
  translate?: string;
  partOfSpeech?: string;
}

export class Word implements IWord {
  constructor(public id?: number, public word?: string, public translate?: string, public partOfSpeech?: string) {}
}
