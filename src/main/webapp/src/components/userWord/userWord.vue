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
      show-select
      :single-select="false"
      v-model="selectWords"
    >
      <template v-slot:header.erase>
        <v-icon @click="eraserSelectWords()">
          mdi-eraser
        </v-icon>
      </template>
      <template v-slot:item.erase="{ item }">
        <v-icon @click.stop="eraserWord(item)">
          mdi-eraser
        </v-icon>
      </template>

      <template v-slot:header.remove>
        <v-icon @click="removeSelectWords()">
          mdi-close
        </v-icon>
      </template>
      <template v-slot:item.remove="{ item }">
        <v-icon @click.stop="removeWord(item)">
          mdi-close
        </v-icon>
      </template>

      <template v-slot:header.know>
        <v-icon @click="knowSelectWords()">
          mdi-head-lightbulb
        </v-icon>
      </template>
      <template v-slot:item.know="{ item }">
        <v-icon @click.stop="knowWord(item)">
          mdi-head-lightbulb
        </v-icon>
      </template>

      <template v-slot:header.learn>
        <v-icon @click="learnSelectWords()">
          mdi-school
        </v-icon>
      </template>

    </v-data-table>
    <div v-intersect="next"></div>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue, Watch } from 'vue-property-decorator'
import UserWordService from '@/services/userWordService'
import { UserWordDto } from '@/model/userWordDto'
import { asc, desc } from '@/util/sortValue'
import FileService from '@/services/fileService'
import { UserWordFilter } from '@/services/filters/userWordFilter'

@Component({
  components: {}
})
export default class UserWord extends Vue {
  @Inject() readonly userWordService!: UserWordService
  @Inject() readonly fileService!: FileService

  public requestCount = 20
  public allElements = false
  public words: UserWordDto[] = []
  public selectWords: UserWordDto[] = []
  public selectAll = false

  public userWordFilter = new UserWordFilter()

  public sortBy: string[] = ['word.word']
  public sortDesc: boolean[] = [false]

  public searchString = ''

  public headers = [
    {
      text: 'Слово',
      value: 'word.word',
      sortable: true
    },
    {
      text: 'Перевод',
      value: 'word.translate',
      sortable: false
    },
    {
      text: 'Часть речи',
      value: 'word.partOfSpeechNote',
      sortable: false
    },
    {
      text: 'Коробка',
      value: 'averageBox',
      sortable: false
    },
    {
      text: '',
      value: 'erase',
      sortable: false,
      width: 20
    },
    {
      text: '',
      value: 'remove',
      sortable: false,
      width: 20
    },
    {
      text: '',
      value: 'know',
      sortable: false,
      width: 20
    },
    {
      text: '',
      value: 'learn',
      sortable: false,
      width: 20
    }
  ]

  public async mounted () {
    this.words = await this.retrieve()
  }

  @Watch('searchString')
  public async searchChange (wordOrTranslate: string) {
    this.userWordFilter.sort.maxValue = undefined
    this.userWordFilter.wordOrTranslate.contains = wordOrTranslate
    this.words = []

    this.retrieve()
  }

  /**
   * Загружает следующую страницу (пагинация)
   */
  public async next () {
    if (!this.allElements && this.words.length > 0) {
      const wordDto: UserWordDto = this.words[this.words.length - 1]
      if (this.userWordFilter.sort.sortField === this.userWordFilter.word) {
        this.userWordFilter.sort.maxValue = wordDto.word?.word
      }

      const nextWords = await this.retrieve()
      if (this.selectAll) {
        this.selectWords = this.selectWords.concat(nextWords)
      }
    }
  }

  public async updateSortBy (value: string[]): Promise<void> {
    const item = value[0]
    if (item === 'word.word') {
      this.userWordFilter.sort.sortField = this.userWordFilter.word
      this.userWordFilter.sort.sortDirection = asc
    }

    this.words = await this.retrieve()
  }

  public async updateSortDesc (value: boolean[]): Promise<void> {
    const direction = value[0]
    this.userWordFilter.sort.sortDirection = direction ? desc : asc
    this.userWordFilter.sort.maxValue = undefined
    this.allElements = false

    this.words = await this.retrieve()
  }

  private async retrieve (): Promise<UserWordDto[]> {
    const words = await this.userWordService.retrieve(this.userWordFilter)
    this.allElements = words.length < this.requestCount
    this.words = this.words.concat(words)
    return words
  }

  /**
   * Сбрасывает прогресс слова
   */
  public eraserWord (word: UserWordDto): void {
    if (word.wordProgresses) {
      word.averageBox = 1
      word.wordProgresses.forEach((progress) => { progress.boxNumber = 1 })
    }
    if (word.word && word.word.id) {
      this.userWordService.eraseWord(word.word.id)
    }
  }

  /**
   * Сбрасывает прогресс выделенных слов
   */
  public eraserSelectWords (): void {
    if (this.selectWords) {
      this.selectWords.forEach(word => {
        if (word.wordProgresses) {
          word.averageBox = 1
          word.wordProgresses.forEach(progress => { progress.boxNumber = 1 })
        }
      })
    }
    if (this.selectAll) {
      this.userWordService.eraseAllWords(this.userWordFilter)
    } else {
      if (this.selectWords) {
        this.userWordService.eraseWords(this.selectWords.map(word => word.word!.id))
      }
    }
  }

  /**
   * Удаление слова из словаря пользователя
   */
  public removeWord (word: UserWordDto): void {
    this.words = this.words.filter(item => item.id !== word.id)
    if (word.word && word.word.id) {
      this.userWordService.removeWord(word.word.id)
    }
  }

  /**
   * Удаление выделенных слов из словаря пользователя
   */
  public removeSelectWords (): void {
    if (this.selectAll) {
      this.userWordService.removeAllWords(this.userWordFilter)
      this.words = []
    } else {
      if (this.words && this.selectWords) {
        this.userWordService.removeWords(this.selectWords.map(word => word.word!.id))
        this.words = this.words.filter(word => !this.selectWords!.some(wordComp => word.id === wordComp.id))
      }
    }
  }

  /**
   * Отмечает слово выученным
   */
  public knowWord (word: UserWordDto): void {
    if (word.wordProgresses) {
      word.averageBox = 7
      word.wordProgresses.forEach((progress) => { progress.boxNumber = 7 }) // todo заменить на enum
    }
    if (word.word && word.word.id) {
      this.userWordService.knowWord(word.word.id)
    }
  }

  /**
   * Отмечает выделенные слова выученными
   */
  public knowSelectWords (): void {
    if (this.selectWords) {
      this.selectWords.forEach(word => {
        if (word.wordProgresses) {
          word.averageBox = 7
          word.wordProgresses.forEach(progress => { progress.boxNumber = 7 })
        }
      })
    }
    if (this.selectAll) {
      const filter = new UserWordFilter()
      this.userWordService.knowAllWords(this.userWordFilter)
    } else {
      if (this.selectWords) {
        this.userWordService.knowWords(this.selectWords.map(word => word.word!.id))
      }
    }
  }

  /**
   * Свободное изучение выделенных слов
   */
  public learnSelectWords (): void {
    // todo
    console.log(this.selectWords)
  }

  @Watch('selectWords')
  public async selectWordsChange (selectWords: UserWordDto[], oldSelectWords: UserWordDto[]) {
    this.selectAll = selectWords.length === this.words.length
  }

  public async clickRow (userWord: UserWordDto) {
    if (userWord && userWord.word && userWord.word.audioId) {
      if (!userWord.word.audioUrl) {
        userWord.word.audioUrl = await this.fileService.getUrl(userWord.word.audioId)
      }
      const audio = new Audio(userWord.word.audioUrl)
      audio.play()
    }
  }
}
</script>
