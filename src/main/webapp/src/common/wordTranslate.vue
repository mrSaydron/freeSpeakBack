<template>
  <v-dialog
    v-model="modal"
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
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue, Watch } from 'vue-property-decorator'
import WordService from '../services/wordService'
import FileService from '../services/fileService'
import { WordDto } from '@/model/wordDto'
import { PartOfSpeechEnum } from '@/model/enums/partOfSpeechEnum'

/**
 * Модальное окно с информацией о слове
 */
@Component({
  components: {
  }
})
export default class WordTranslate extends Vue {
  @Prop(Object) readonly wordModal: boolean | undefined
  @Prop(Object) readonly wordId: number | undefined

  @Inject() readonly wordService!: WordService
  @Inject() readonly fileService!: FileService

  public modal = false
  public wordDto: WordDto = {}
  public word = ''
  public translate = ''
  public partOfSpeech = ''

  @Watch('modal')
  public modalWatch (modal: boolean): void {
    console.log('WordTranslate: modalWatch')
    if (!modal) {
      this.$emit('modal-close')
    }
  }

  @Watch('wordModal')
  public async wordModalWatch (wordModal: boolean): Promise<void> {
    console.log('WordTranslate: wordModalWatch')
    this.modal = wordModal
    if (wordModal) {
      if (this.wordId) {
        this.wordDto = await this.wordService.find(this.wordId)
        this.word = this.wordDto.word || ''
        this.translate = this.wordDto.translate || ''
        this.partOfSpeech = this.wordDto.partOfSpeech ? PartOfSpeechEnum[this.wordDto.partOfSpeech] : ''

        if (this.wordDto && this.wordDto.audioId) {
          this.wordDto.audioUrl = await this.fileService.getUrl(this.wordDto.audioId)
          const audio = new Audio(this.wordDto.audioUrl)
          await audio.play()
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
