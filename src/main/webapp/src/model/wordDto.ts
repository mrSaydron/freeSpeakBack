import { PartOfSpeechEnum } from '@/model/enums/partOfSpeechEnum'

export interface Word {
  id?: number;
  word?: string;
  partOfSpeech?: string;
  translate?: string;
  totalAmount?: number;
  pictureId?: string;
  audioId?: string;
  frequency?: number;
  frequencyPercent?: string;
  userHas?: boolean;
  pictureUrl?: string;
  audioUrl?: string;
}

export class WordDto implements Word {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public id?: number,
    public word?: string,
    public partOfSpeech?: string,
    public translate?: string,
    public totalAmount?: number,
    public pictureId?: string,
    public audioId?: string,
    public frequency?: number,
    public frequencyPercent?: string,
    public partOfSpeechNote?: any,
    public userHas?: boolean,
    public pictureUrl?: string,
    public audioUrl?: string
  ) {}

  /**
   * Запольняет слово полями для отображения
   */
  public static fill (word: WordDto): void {
    if (word.frequency) {
      word.frequencyPercent = (word.frequency * 100).toFixed(2) + ' %'
    }
    if (word.partOfSpeech) {
      word.partOfSpeechNote = PartOfSpeechEnum[word.partOfSpeech]
    }
  }
}
