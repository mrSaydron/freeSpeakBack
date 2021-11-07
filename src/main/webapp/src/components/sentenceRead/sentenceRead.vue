<template>
  <v-container>
    <v-row v-if="sentence">
      <v-col>
        {{ sentence.book.title }}
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-card v-if="bookSentences.length === 0">
          <v-card-title class="justify-center">
            Предложений нет, изучите еще слова
          </v-card-title>
        </v-card>
        <sentence-card
          v-else
          :sentence="sentence"
          @not-translate="notTranslate"
          @translate="translate"
        ></sentence-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import SentenceCard from '@/components/sentenceRead/sentenceCard.vue'
import { BookSentenceReadDto } from '@/model/bookSentenceReadDto'
import BookSentenceService from '@/services/bookSentenceService'

/**
 * Тренеровка перевода передложений
 */
@Component({
  components: {
    SentenceCard
  }
})
export default class SentenceRead extends Vue {
  @Inject() readonly bookSentenceService!: BookSentenceService

  public bookSentences: BookSentenceReadDto[] = []
  public sentence: BookSentenceReadDto | null = null
  public index = 0

  public async mounted () {
    this.bookSentences = await this.bookSentenceService.findToRead()
    if (this.bookSentences.length > 0) {
      this.sentence = this.bookSentences[this.index]
    }
  }

  public translate (): void {
    if (this.sentence) {
      this.bookSentenceService.successfulTranslate(this.sentence.id!)
      this.index++
      if (this.bookSentences.length <= this.index) {
        this.bookSentences = []
        this.sentence = null
      } else {
        this.sentence = this.bookSentences[this.index]
      }
    }
  }

  public notTranslate (): void {
    if (this.sentence) {
      this.bookSentenceService.failTranslate(this.sentence.id!)
      this.index++
      if (this.bookSentences.length <= this.index) {
        this.bookSentences = []
        this.sentence = null
      } else {
        this.sentence = this.bookSentences[this.index]
      }
    }
  }
}
</script>

<style scoped>

</style>
