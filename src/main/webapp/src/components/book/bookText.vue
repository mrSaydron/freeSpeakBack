<template>
  <v-container>
    <span v-for="bookSentence in bookSentences" :key="bookSentence.id">
      <span
        v-for="(word, index) in bookSentence.words"
        :key="index"
      >
        <span
          v-if="word.translateId"
          class="pre-wrap hover-span"
          @click="wordClick(word.translateId)"
        >{{word.word}}{{word.afterWord}}</span>
        <span
          v-if="!word.translateId"
          class="pre-wrap"
        >{{word.word}}{{word.afterWord}}</span>
      </span>
    </span>
    <v-dialog
      v-model="wordModal"
      max-width="300px"
    >
      <v-card>
        <v-card-title>{{ word }}</v-card-title>
        <v-card-text>
          <p>{{ partOfSpeech }}</p>
          <p>{{ translate }}</p>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue } from 'vue-property-decorator'
import { BookSentenceDto } from '@/model/bookSentenceDto'
import { WordDto } from '@/model/wordDto'
import WordService from '@/services/wordService'
import { PartOfSpeechEnum } from '@/model/enums/partOfSpeechEnum'
import FileService from '@/services/fileService'

@Component({
  components: {}
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

  public async wordClick (translateId: number) {
    console.log(translateId)
    if (translateId) {
      this.wordId = translateId
      this.wordDto = await this.wordService.find(translateId)
      this.word = this.wordDto.word || ''
      this.translate = this.wordDto.translate || ''
      this.partOfSpeech = this.wordDto.partOfSpeech ? PartOfSpeechEnum[this.wordDto.partOfSpeech] : ''
      this.wordModal = true

      if (this.wordDto && this.wordDto.audioId) {
        this.wordDto.audioUrl = await this.fileService.getUrl(this.wordDto.audioId)
        const audio = new Audio(this.wordDto.audioUrl)
        await audio.play()
      }
    }
  }
}
</script>

<style>
span.pre-wrap {
  white-space: pre-wrap;
}
span.hover-span:hover {
  background: #f8b8b8;
}
</style>
