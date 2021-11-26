<template>
  <v-container>
    <span v-for="bookSentence in bookSentences" :key="bookSentence.id">
      <sentence
        :book-sentence="bookSentence"
        @word-click="wordClick"
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
import { Inject, Prop, Vue } from 'vue-property-decorator'
import { BookSentenceDto } from '@/model/bookSentenceDto'
import { WordDto } from '@/model/wordDto'
import WordService from '@/services/wordService'
import FileService from '@/services/fileService'
import Sentence from '@/common/sentence/sentence.vue'
import SentenceWord from '@/common/sentence/sentenceWord.vue'

@Component({
  components: {
    Sentence,
    SentenceWord
  }
})
export default class BookText extends Vue {
  @Prop(String) readonly bookSentences?: BookSentenceDto[]

  @Inject() readonly wordService!: WordService
  @Inject() readonly fileService!: FileService

  public wordModal = false
  public wordId: number | null = null
  public wordDto: WordDto = {}
  public word = ''
  public translate = ''
  public partOfSpeech = ''
  public userHas = false

  public wordClick (wordId: number): void {
    this.wordId = wordId
    this.wordModal = true
  }

  public modalClose (): void {
    this.wordModal = false
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
