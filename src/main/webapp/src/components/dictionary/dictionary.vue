import { SortDirection } from '@/model/enums/sortDirection'
<template>
  <v-container>
    <v-text-field
      v-model="searchString"
      label="Слово или перевод"
      clearable
      prepend-inner-icon="mdi-magnify"
    ></v-text-field>
    <v-data-table
      :headers="headers"
      :items="words"
      :disable-pagination="true"
      hide-default-footer
      @update:sort-by="updateSortBy"
      @update:sort-desc="updateSortDesc"
      :server-items-length="words.length"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      @click:row="clickRow"
    >
      <template v-slot:item.action="{ item }">
        <v-icon
          v-if="!item.userHas"
          class="mr-2"
          @click.stop="addWord(item)"
        >
          mdi-plus
        </v-icon>
        <v-icon
          v-else
          @click.stop="removeWord(item)"
        >
          mdi-close
        </v-icon>
      </template>
    </v-data-table>
    <div v-intersect="next"></div>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue, Watch } from 'vue-property-decorator'

import WordService from '@/services/wordService'
import { WordDto } from '@/model/wordDto'
import { asc, desc, SortValue } from '@/util/sortValue'
import UserWordService from '@/services/userWordService'
import FileService from '@/services/fileService'

@Component({
  components: {}
})
export default class Dictionary extends Vue {
  @Inject() readonly wordService!: WordService
  @Inject() readonly userWordService!: UserWordService
  @Inject() readonly fileService!: FileService

  public requestCount = 20
  public allElements = false
  public words: WordDto[] = []
  public loading = false
  public sortBy: string[] = ['word']
  public sortDesc: boolean[] = [false]

  public searchString = ''
  public wordSort?: SortValue
  public amountSort?: SortValue

  public headers = [
    {
      text: 'Слово',
      value: 'word',
      sortable: true
    },
    {
      text: 'Перевод',
      value: 'translate',
      sortable: false
    },
    {
      text: 'Часть речи',
      value: 'partOfSpeechNote',
      sortable: false
    },
    {
      text: 'Частота',
      value: 'frequencyPercent',
      sortable: true
    },
    {
      text: '',
      value: 'action',
      sortable: false
    }
  ]

  public async mounted () {
    this.loading = true
    this.words = await this.retrieve()
    this.loading = false
  }

  @Watch('searchString')
  public async searchChange (wordFilter: string, oldWordFilter: string) {
    this.loading = true
    if (this.wordSort) {
      this.wordSort.maxValue = undefined
    }
    if (this.amountSort) {
      this.amountSort.maxValue = undefined
    }
    this.words = await this.retrieve()
    this.loading = false
  }

  public async next () {
    if (!this.allElements && this.words.length > 0) {
      const wordDto: WordDto = this.words[this.words.length - 1]
      if (this.wordSort) {
        this.wordSort.maxValue = wordDto.word
      }
      if (this.amountSort) {
        this.amountSort.maxValue = wordDto.totalAmount
      }
      const nextWords = await this.retrieve()
      this.words = this.words.concat(nextWords)
    }
  }

  public async updateSortBy (value: string[]): Promise<void> {
    const item = value[0]
    if (item === 'word') {
      // this.wordSort = new SortValue(undefined, asc)
      this.amountSort = undefined
    } else if (item === 'frequencyPercent') {
      this.wordSort = undefined
      // this.amountSort = new SortValue(undefined, asc)
    }

    this.words = await this.retrieve()
  }

  public async updateSortDesc (value: boolean[]): Promise<void> {
    const direction = value[0]
    if (this.wordSort) {
      this.wordSort.sortDirection = direction ? desc : asc
    }
    if (this.amountSort) {
      this.amountSort.sortDirection = direction ? desc : asc
    }

    this.words = await this.retrieve()
  }

  private async retrieve (): Promise<WordDto[]> {
    const searchString = this.searchString === '' ? undefined : this.searchString

    const words = await this.wordService.retrieve(
      searchString,
      undefined,
      this.wordSort,
      this.amountSort,
      this.requestCount)
    this.allElements = words.length < this.requestCount

    return words
  }

  /**
   * Добавление слова в словарь пользователя
   */
  public addWord (word: WordDto): void {
    word.userHas = !word.userHas
    if (word.id) {
      this.userWordService.addWord(word.id)
    }
  }

  /**
   * Удаление слова из словаря пользователя
   */
  public removeWord (word: WordDto): void {
    word.userHas = !word.userHas
    if (word.id) {
      this.userWordService.removeWord(word.id)
    }
  }

  public async clickRow (word: WordDto) {
    if (word && word.audioId) {
      if (!word.audioUrl) {
        word.audioUrl = await this.fileService.getUrl(word.audioId)
      }
      const audio = new Audio(word.audioUrl)
      await audio.play()
    }
  }
}
</script>
