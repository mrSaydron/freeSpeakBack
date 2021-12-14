<template>
  <v-container>
    <v-row v-if="isWord && word && word.word">
      <v-col>
        <word-card-direct-learn
          :word="word.word"
          @not-remember="answerFail"
          @remember="answerSuccess"
        ></word-card-direct-learn>
      </v-col>
    </v-row>
    <v-row v-if="isResult">
      <v-col>
        <v-card>
          <v-card-title class="text-center">
            Твой словарный запас составляет:
          </v-card-title>
          <v-card-text class="text-center text-h1">
            {{ result.result.vocabulary }}
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import TestVocabularyService from '@/services/testVocabularyService'
import { TestVocabularyDto } from '@/model/testVocabulary/testVocabularyDto'
import { TestVocabularyTypeEnum } from '@/model/enums/testVocabularyTypeEnum'
import { TestVocabularyWordDto } from '@/model/testVocabulary/testVocabularyWordDto'
import { TestVocabularyResultDto } from '@/model/testVocabulary/testVocabularyResultDto'
import { Word } from '@/model/wordDto'
import WordCardDirectTest from '@/common/wordCard/wordCardDirectTest.vue'

@Component({
  components: {
    WordCardDirectLearn: WordCardDirectTest
  }
})
export default class TestVocabulary extends Vue {
  @Inject() readonly testVocabularyService!: TestVocabularyService

  public isResult = false
  public isWord = false
  public word: TestVocabularyWordDto = {}
  public nextWord: Word = {}
  public result: TestVocabularyResultDto = {}

  public async mounted (): Promise<void> {
    const testVocabulary = await this.testVocabularyService.start()
    this.next(testVocabulary)
  }

  public next (testVocabulary: TestVocabularyDto): void {
    if (testVocabulary.type === TestVocabularyTypeEnum.WORD) {
      this.isWord = true
      this.word = testVocabulary
      if (this.word.word) {
        this.nextWord = this.word.word
      }
    } else if (testVocabulary.type === TestVocabularyTypeEnum.RESULT) {
      this.isWord = false
      this.isResult = true
      this.result = testVocabulary
    }
  }

  public async answerFail (): Promise<void> {
    if (this.word && this.word.word) {
      const testVocabulary = await this.testVocabularyService.answerFail(
        this.word.word.id!,
        this.word.testVocabularyId!
      )
      this.next(testVocabulary)
    }
  }

  public async answerSuccess (): Promise<void> {
    if (this.word && this.word.word) {
      const testVocabulary = await this.testVocabularyService.answerSuccess(
        this.word.word.id!,
        this.word.testVocabularyId!
      )
      this.next(testVocabulary)
    }
  }
}
</script>

<style scoped>

</style>
