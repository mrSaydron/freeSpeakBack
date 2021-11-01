<template>
  <v-container>
    <v-toolbar elevation="0">
      <sort-button
        text="Название"
        :direction="nameDirection"
        @update:direction="titleClick"
      >
      </sort-button>
      <sort-button
        text="Автор"
        :direction="authorDirection"
        @update:direction="authorClick"
      >
      </sort-button>
      <v-divider :vertical="true"></v-divider>
      <select-button
        text="Легко"
        :select="knowGreen"
        @update:select="knowGreenClick"
      >
      </select-button>
      <select-button
        text="Средне"
        :select="knowYellow"
        @update:select="knowYellowClick"
      >
      </select-button>
      <select-button
        text="Сложно"
        :select="knowRed"
        @update:select="knowRedClick"
      >
      </select-button>
      <v-text-field
        v-model="searchString"
        label="Название или автор (минимум три символа)"
        clearable
        prepend-icon="mdi-magnify"
        single-line
        hide-details
      ></v-text-field>
    </v-toolbar>

    <v-container fluid>
      <v-row dense>
        <book-card
          v-for="book in books"
          :key="book.id"
          :book="book"
        ></book-card>
      </v-row>
    </v-container>
    <div v-intersect="next"></div>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue, Watch } from 'vue-property-decorator'

import BookCard from '@/common/bookCard.vue'
import BookService from '@/services/bookService'
import { BookDto } from '@/model/bookDto'
import { BookFilter } from '@/services/filters/bookFilter'
import { asc, SortDirection } from '@/util/sortValue'
import SortButton from '@/common/sortButton.vue'
import SelectButton from '@/common/selectButton.vue'
import FileService from '@/services/fileService'
import { knowLevels } from '@/model/enums/knowLevelEnum'

@Component({
  components: {
    BookCard,
    SortButton,
    SelectButton
  }
})
export default class Library extends Vue {
  @Inject() readonly bookService!: BookService
  @Inject() readonly fileService!: FileService

  public requestCount = 20
  public allElements = false
  public searchString = ''
  public books: BookDto[] = []
  public bookFilter = new BookFilter(this.requestCount)

  public nameDirection: SortDirection | null = asc
  public authorDirection: SortDirection | null = null
  public knowGreen = false
  public knowYellow = false
  public knowRed = false

  public async mounted () {
    await this.retrieve()
  }

  @Watch('searchString')
  public async searchChange (common: string, oldCommon: string) {
    if (common && common !== '' && common.length >= 3) {
      this.bookFilter.titleAuthor.contains = common

      this.books = []
      await this.retrieve()
    } else if (oldCommon && oldCommon !== '' && oldCommon.length >= 3) {
      this.bookFilter.titleAuthor.contains = undefined
      this.books = []
      await this.retrieve()
    }
  }

  public async retrieve (): Promise<void> {
    const nextBooks: BookDto[] = await this.bookService.retrieve(this.bookFilter)
    nextBooks.forEach(book => {
      if (book.pictureId) {
        this.fileService.getUrl(book.pictureId)
          .then(res => {
            book.pictureUrl = res
          })
      }
    })
    this.books = this.books.concat(nextBooks)
    this.allElements = this.books.length < this.requestCount
  }

  public async next () {
    if (!this.allElements && this.books.length > 0) {
      const lastBook = this.books[this.books.length - 1]
      if (this.bookFilter.sort.sortField === this.bookFilter.title) {
        this.bookFilter.sort.maxValue = lastBook.title
      }
      if (this.bookFilter.sort.sortField === this.bookFilter.author) {
        this.bookFilter.sort.maxValue = lastBook.author
      }
      await this.retrieve()
    }
  }

  public titleClick (direction: SortDirection): void {
    this.nameDirection = direction
    this.authorDirection = null

    this.bookFilter.sort.sortDirection = direction
    this.bookFilter.sort.sortField = this.bookFilter.title

    this.books = []
    this.retrieve()
  }

  public authorClick (direction: SortDirection): void {
    this.authorDirection = direction
    this.nameDirection = null

    this.bookFilter.sort.sortDirection = direction
    this.bookFilter.sort.sortField = this.bookFilter.author

    this.books = []
    this.retrieve()
  }

  public knowGreenClick (select: boolean): void {
    this.knowGreen = select
    if (select) {
      this.knowYellow = false
      this.knowRed = false
      this.bookFilter.know.lessThan = undefined
      this.bookFilter.know.lessThanOrEqual = 1.0
      this.bookFilter.know.greaterThanOrEqual = knowLevels[2].know
    } else {
      this.bookFilter.know.lessThan = undefined
      this.bookFilter.know.lessThanOrEqual = undefined
      this.bookFilter.know.greaterThanOrEqual = undefined
    }

    this.bookFilter.sort.maxValue = undefined

    this.books = []
    this.retrieve()
  }

  public knowYellowClick (select: boolean): void {
    this.knowYellow = select
    if (select) {
      this.knowGreen = false
      this.knowRed = false
      this.bookFilter.know.lessThan = knowLevels[2].know
      this.bookFilter.know.lessThanOrEqual = undefined
      this.bookFilter.know.greaterThanOrEqual = knowLevels[1].know
    } else {
      this.bookFilter.know.lessThan = undefined
      this.bookFilter.know.lessThanOrEqual = undefined
      this.bookFilter.know.greaterThanOrEqual = undefined
    }

    this.bookFilter.sort.maxValue = undefined

    this.books = []
    this.retrieve()
  }

  public knowRedClick (select: boolean): void {
    this.knowRed = select
    if (select) {
      this.knowGreen = false
      this.knowYellow = false
      this.bookFilter.know.lessThan = knowLevels[1].know
      this.bookFilter.know.lessThanOrEqual = undefined
      this.bookFilter.know.greaterThanOrEqual = knowLevels[0].know
    } else {
      this.bookFilter.know.lessThan = undefined
      this.bookFilter.know.lessThanOrEqual = undefined
      this.bookFilter.know.greaterThanOrEqual = undefined
    }

    this.bookFilter.sort.maxValue = undefined

    this.books = []
    this.retrieve()
  }
}
</script>
