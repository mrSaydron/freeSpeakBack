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
          :word="word"
          @not-remember="answerFail"
          @remember="answerSuccess"
        ></word-card-direct>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import WordCardDirect from '@/common/wordCard/wordCardDirect.vue'
import { WordDto } from '@/model/wordDto'
import UserWordService from '@/services/userWordService'
import { CardTypeEnum } from '@/model/enums/cardTypeEnum'
import { asc, SortValue } from '@/util/sortValue'
import { UserWordFilter } from '@/services/filters/userWordFilter'
import { Card } from '@/model/card'

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
    }
  }

  get word (): WordDto {
    return (this.card && this.card.userWord && this.card.userWord.word) || {}
  }

  get cardType (): string {
    let result = 'plug'
    if (this.card && this.card.wordProgress && this.card.wordProgress.type) {
      result = CardTypeEnum[this.card.wordProgress.type]
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
    if (!this.allElements && this.leftHearts > 0) {
      const filter = new UserWordFilter()
      filter.boxNumber.equals = 0
      filter.sort.sortField = filter.priority
      filter.sort.sortDirection = asc

      const words = await this.userWordService.retrieve(filter)
      this.allElements = words.length < this.requestCount
      this.cards = this.cards.concat(Card.transform(words))
    }
  }
}
</script>
