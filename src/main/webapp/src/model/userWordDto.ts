import { WordDto } from '@/model/wordDto'
import { WordProgressDto } from '@/model/wordProgressDto'

export interface UserWord {
  id?: number;
  priority?: number;
  word?: WordDto;
  wordProgresses?: WordProgressDto[];
}

export class UserWordDto implements UserWord {
  constructor (
    public id?: number,
    public priority?: number,
    public word?: WordDto,
    public wordProgresses?: WordProgressDto[],
    public averageBox?: number,
    public selected?: boolean
  ) {
    console.log('UserWordDto')
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
