<template>
  <v-container>
    <v-row>
      <v-col>
        <v-rating
          length="10"
          :value="leftHearts"
          readonly
          color="red"
          background-color="red lighten-2"
          empty-icon="mdi-heart-outline"
          full-icon="mdi-heart"
        >
        </v-rating>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-card
          v-if="cardType === 'plug'"
        >
          <v-card-title class="justify-center">
            На сегодня слов больше нет
          </v-card-title>
        </v-card>
        <word-card-direct
          v-if="cardType === 'direct'"
          :userWord="card.userWord"
          @not-remember="answerFail"
          @remember="answerSuccess"
          @know="knowWord"
        ></word-card-direct>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import WordCardDirect from '@/common/wordCard/wordCardDirect.vue'
import UserWordService from '@/services/userWordService'
import { SortValue } from '@/util/sortValue'
import { Card } from '@/model/card'
import { UserWordDto } from '@/model/userWordDto'

@Component({
  components: {
    WordCardDirect
  }
})
export default class CardsLearn extends Vue {
  @Inject() readonly userWordService!: UserWordService

  public card: Card | null = null
  public cards: Card[] = []
  public leftHearts = 0

  public requestCount = 20
  public allElements = false
  public wordSort?: SortValue

  public async mounted () {
    console.log('learn mounted')
    this.leftHearts = await this.userWordService.getLeftHearts()
    if (this.leftHearts > 0) {
      const words = await this.userWordService.getWordOfDay()
      this.cards = Card.transform(words)
      if (this.cards.length === 0) {
        await this.nextNewWords()
      }
      if (this.cards.length > 0) {
        this.card = this.cards[0]
      }
      console.log('card')
      console.log(this.card)
    }
  }

  get word (): UserWordDto {
    return (this.card && this.card.userWord) || {}
  }

  get cardType (): string {
    let result = 'plug'
    if (this.card && this.card.wordProgress && this.card.wordProgress.type) {
      result = this.card.wordProgress.type
    }
    return result
  }

  /**
   * Если пользователь ответил не вероно:
   * - отправляем сообщение на бэк
   * - слово оставляем до верного ответа
   * - если слов меньше 10, то загужаем следующие слова
   * - слово перемещаем на десять карточек назад
   * - если закончились "жизни" то все новые слова отбрасываются
   */
  public async answerFail (): Promise<void> {
    this.userWordService.answerFail(this.card!)
    const currentCard = this.cards.shift()
    if (this.cards.length < 10) {
      await this.nextNewWords()
    }
    if (this.cards.length > 10) {
      this.cards.splice(9, 0, currentCard!)
    } else {
      this.cards.push(currentCard!)
    }
    this.card = this.cards[0]

    this.leftHearts--
    if (this.leftHearts < 0) this.leftHearts = 0
    this.card.answerFailCount++

    if (this.leftHearts === 0) {
      this.cards = this.cards.filter(card => card.answerFailCount !== 0)
    }
  }

  /**
   * Если пользователь ответил верно:
   * - отправляем сообщение на бэк
   * - убираем слово из списка
   * - проверяем остались ли слова
   */
  public async answerSuccess (): Promise<void> {
    this.userWordService.answerSuccess(this.card!)
    this.cards.shift()
    if (this.cards.length > 0) {
      this.card = this.cards[0]
    } else {
      this.card = null
      await this.nextNewWords()
    }
  }

  /**
   * Закончились слова, загружаем следующий блок еще не изученных слов
   */
  public async nextNewWords (): Promise<void> {
    console.log('learn next')
    if (!this.allElements && this.leftHearts > 0) {
      const words = await this.userWordService.nextWords()
      this.allElements = words.length < this.requestCount
      this.cards = this.cards.concat(Card.transform(words))
      console.log(this.cards)
    }
  }

  /**
   * Пользователь знает слово, оно переносится сразу в последнюю коробку
   */
  public async knowWord (): Promise<void> {
    if (this.card && this.card.userWord.word && this.card.userWord.word.id) {
      this.userWordService.knowWord(this.card.userWord.word.id)
      this.cards.shift()
      if (this.cards.length > 0) {
        this.card = this.cards[0]
      } else {
        this.card = null
        await this.nextNewWords()
      }
    }
  }
}
</script>
