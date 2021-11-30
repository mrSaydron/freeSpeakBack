<template>
  <v-dialog
    v-model="modal"
    max-width="450px"
  >
    <v-card>
      <v-card-title>{{ word }}</v-card-title>
      <v-card-text>
        <p>{{ partOfSpeech }}</p>
        <p>{{ translate }}</p>
        <p v-if="userWordDto.averageBox > 0">
          Номер коробки: {{ userWordDto.averageBox }}
        </p>
      </v-card-text>
      <v-card-actions>
        <div v-if="userWordDto.id">
          <v-btn
            text
            @click="eraseWord"
            v-if="userWordDto.averageBox > START_BOX_NUMBER"
          >Сбросить прогресс</v-btn>

          <v-btn
            text
            @click="knowWord"
            v-if="userWordDto.averageBox < KNOW_BOX_NUMBER"
          >Знаю</v-btn>
        </div>
        <div v-else>
          <v-btn
            text
            @click="addWord"
          >Учить</v-btn>

          <v-btn
            text
            @click="knowWord"
          >Знаю</v-btn>
        </div>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue, Watch } from 'vue-property-decorator'
import FileService from '../../services/fileService'
import { PartOfSpeechEnum } from '@/model/enums/partOfSpeechEnum'
import UserWordService from '@/services/userWordService'
import { UserWordDto } from '@/model/userWordDto'
import { Constants } from '@/model/enums/constants'

/**
 * Модальное окно с информацией о слове
 */
@Component({
  components: {
  }
})
export default class SentenceWord extends Vue {
  @Prop(Object) readonly wordModal: boolean | undefined
  @Prop(Object) readonly wordId: number | undefined

  @Inject() readonly userWordService!: UserWordService
  @Inject() readonly fileService!: FileService

  public PRELIMINARY_BOX_NUMBER = Constants.PRELIMINARY_BOX_NUMBER
  public START_BOX_NUMBER = Constants.START_BOX_NUMBER
  public KNOW_BOX_NUMBER = Constants.KNOW_BOX_NUMBER

  public modal = false
  public userWordDto: UserWordDto = {}
  public word = ''
  public translate = ''
  public partOfSpeech = ''

  @Watch('modal')
  public modalWatch (modal: boolean): void {
    if (!modal) {
      this.$emit('modal-close')
    }
  }

  @Watch('wordModal')
  public async wordModalWatch (wordModal: boolean): Promise<void> {
    this.modal = wordModal
    if (wordModal) {
      if (this.wordId) {
        this.userWordDto = await this.userWordService.get(this.wordId)
        if (this.userWordDto && this.userWordDto.word) {
          this.word = this.userWordDto.word.word || ''
          this.translate = this.userWordDto.word.translate || ''
          this.partOfSpeech = this.userWordDto.word.partOfSpeech ? PartOfSpeechEnum[this.userWordDto.word.partOfSpeech] : ''
        }

        if (this.userWordDto && this.userWordDto.word && this.userWordDto.word.audioId) {
          this.userWordDto.word.audioUrl = await this.fileService.getUrl(this.userWordDto.word.audioId)
          const audio = new Audio(this.userWordDto.word.audioUrl)
          await audio.play()
        }
      }
    }
  }

  public eraseWord (): void {
    if (this.wordId) {
      this.userWordService.eraseWord(this.wordId)
      this.modal = false
    }
  }

  public knowWord (): void {
    if (this.wordId) {
      this.userWordService.knowWord(this.wordId)
      this.modal = false
    }
  }

  public addWord (): void {
    if (this.wordId) {
      this.userWordService.addWord(this.wordId)
      this.modal = false
    }
  }
}
</script>

<style scoped>

</style>
