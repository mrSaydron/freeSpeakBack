import { WordProgressDto } from '@/model/wordProgressDto'
import { UserWordDto } from '@/model/userWordDto'

export class Card {
  id: number
  answerFailCount: number

  constructor (
    public wordProgress: WordProgressDto,
    public userWord: UserWordDto
  ) {
    this.id = userWord.id!
    this.answerFailCount = 0
  }

  public static transform (userWords: UserWordDto[]): Card[] {
    let result: Card[] = []
    userWords.forEach(userWord => {
      if (userWord.wordProgresses) {
        const cards = userWord.wordProgresses.map(progress => new Card(progress, userWord))
        result = result.concat(cards)
      }
    })
    return result
  }
}
