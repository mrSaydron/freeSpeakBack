<template>
  <v-container v-if="bookSentencesUnion">
    <span
      v-for="bookSentence in bookSentencesUnion.sentencesDto"
      :key="bookSentence.id"
    >
      <sentence
        :book-sentence="bookSentence"
        @word-click="wordClick"
        v-intersect.quiet="sentenceIntersect"
        :id="'sentence_id_' + bookSentence.id"
      ></sentence>
    </span>
    <sentence-word
      :word-id="wordId"
      :word-modal="wordModal"
      @modal-close="modalClose"
    ></sentence-word>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue, Watch } from 'vue-property-decorator'
import { BookSentenceDto } from '@/model/bookSentenceDto'
import { WordDto } from '@/model/wordDto'
import WordService from '@/services/wordService'
import FileService from '@/services/fileService'
import Sentence from '@/common/sentence/sentence.vue'
import SentenceWord from '@/common/sentence/sentenceWord.vue'
import { BookSentenceUnionDto } from '@/model/bookSentenceUnionDto'

@Component({
  components: {
    Sentence,
    SentenceWord
  }
})
export default class BookText extends Vue {
  @Prop(Object) readonly bookSentencesUnion?: BookSentenceUnionDto

  @Inject() readonly wordService!: WordService
  @Inject() readonly fileService!: FileService

  public wordModal = false
  public wordId: number | null = null
  public wordDto: WordDto = {}
  public word = ''
  public translate = ''
  public partOfSpeech = ''
  public userHas = false
  public bookmarkSentenceId?: number

  public updated (): void {
    if (this.bookSentencesUnion) {
      this.bookmarkReset(this.bookSentencesUnion)
    }
  }

  /**
   * Инициализируем переменную с идентификатором верхнего предложения, и прокручиваем страницу до него
   * @param bookSentencesUnion
   */
  public bookmarkReset (bookSentencesUnion: BookSentenceUnionDto): void {
    if (
      bookSentencesUnion.bookDto.id &&
      bookSentencesUnion.sentencesDto &&
      bookSentencesUnion.sentencesDto.length > 0
    ) {
      const sentenceId = localStorage.getItem('book_' + bookSentencesUnion.bookDto.id)
      if (sentenceId) {
        this.bookmarkSentenceId = parseInt(sentenceId)
      } else {
        this.bookmarkSentenceId = bookSentencesUnion.sentencesDto[0].id
        if (this.bookmarkSentenceId) {
          localStorage.setItem('book_' + bookSentencesUnion.bookDto.id, this.bookmarkSentenceId.toString())
        }
      }
      if (
        bookSentencesUnion.sentencesDto &&
        bookSentencesUnion.sentencesDto.length > 0 &&
        this.bookmarkSentenceId !== bookSentencesUnion.sentencesDto[0].id
      ) {
        this.goToSentence()
      }
    }
  }

  public goToSentence (): void {
    const sentence = window.document.getElementById('sentence_id_' + this.bookmarkSentenceId)
    if (sentence) {
      sentence.scrollIntoView()
    }
  }

  public wordClick (wordId: number): void {
    this.wordId = wordId
    this.wordModal = true
  }

  public modalClose (): void {
    this.wordModal = false
  }

  public sentenceIntersect (entries: any, observer: any, isIntersecting: any): void {
    if (this.bookmarkSentenceId && entries.length > 0) {
      const sentenceIdString = entries[0].target.id.substring(12)
      if (sentenceIdString) {
        const sentenceId = parseInt(sentenceIdString)
        if (isIntersecting) {
          if (sentenceId + 1 === this.bookmarkSentenceId) {
            this.bookmarkSentenceId = sentenceId
            localStorage.setItem('book_' + this.bookSentencesUnion!.bookDto.id, this.bookmarkSentenceId.toString())
          }
        } else {
          if (sentenceId === this.bookmarkSentenceId) {
            this.bookmarkSentenceId = this.bookmarkSentenceId + 1
            localStorage.setItem('book_' + this.bookSentencesUnion!.bookDto.id, this.bookmarkSentenceId.toString())
          }
        }
      }
    }
  }
}
</script>

<style scoped>
span.pre-wrap {
  white-space: pre-wrap;
}
span.hover-span:hover {
  background: #f8b8b8;
}
</style>
