import { WordDto } from '@/model/wordDto'
import { WordProgressDto } from '@/model/wordProgressDto'

export interface UserWord {
  id?: number;
  word?: WordDto;
  wordProgresses?: WordProgressDto[];
  fromTest?: boolean;
}

export class UserWordDto implements UserWord {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public id?: number,
    public word?: WordDto,
    public wordProgresses?: WordProgressDto[],
    public averageBox?: number,
    public selected?: boolean,
    public fromTest?: boolean
  ) {
  }

  public static fill (userWord: UserWordDto) {
    userWord.averageBox = 0
    if (userWord.wordProgresses) {
      userWord.averageBox = userWord.wordProgresses
        .reduce((acc: number, item) => acc + (item.boxNumber || 0), 0) / userWord.wordProgresses.length
    }
    if (userWord.word) {
      WordDto.fill(userWord.word)
    }
  }
}
