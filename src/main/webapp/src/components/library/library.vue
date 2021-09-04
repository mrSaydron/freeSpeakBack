<template>
  <v-container>
    <v-toolbar elevation="0">
      <sort-button
        text="Название"
        :direction="nameDirection"
        @update:direction="nameClick"
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
import { SortValue, asc, desc, SortDirection } from '@/util/sortValue'
import SortButton from '@/common/sortButton.vue'
import SelectButton from '@/common/selectButton.vue'
import FileService from '@/services/fileService'
import { DefaultNamesEnum } from '@/model/enums/defaultNamesEnum'
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
  public bookFilter = new BookFilter(
    undefined,
    true,
    undefined,
    new SortValue<string>(undefined, asc),
    undefined,
    this.requestCount
  )

  public nameDirection: SortDirection | null = asc
  public authorDirection: SortDirection | null = null
  public knowGreen = false
  public knowYellow = false
  public knowRed = false

  public async mounted () {
    await this.retrieve(true)
  }

  @Watch('searchString')
  public async searchChange (common: string, oldCommon: string) {
    if (common && common !== '' && common.length >= 3) {
      this.bookFilter.titleAuthorFilter = common
      await this.retrieve(false)
    } else if (oldCommon && oldCommon !== '' && oldCommon.length >= 3) {
      this.bookFilter.titleAuthorFilter = undefined
      await this.retrieve(false)
    }
  }

  public async retrieve (add: boolean): Promise<void> {
    const nextBooks = await this.bookService.retrieve(this.bookFilter)
    nextBooks.forEach(book => {
      const name = book.pictureName ? book.pictureName : DefaultNamesEnum.book
      this.fileService.getUrl(name)
        .then(res => {
          book.pictureUrl = res
        })
    })
    if (add) {
      this.books = this.books.concat(nextBooks)
    } else {
      this.books = nextBooks
    }
    this.allElements = this.books.length < this.requestCount
  }

  public async next () {
    if (!this.allElements && this.books.length > 0) {
      const lastBook = this.books[this.books.length - 1]
      if (this.bookFilter.titleSort) {
        this.bookFilter.titleSort.maxValue = lastBook.title
      }
      if (this.bookFilter.authorSort) {
        this.bookFilter.authorSort.maxValue = lastBook.author
      }
      await this.retrieve(true)
    }
  }

  public nameClick (direction: SortDirection): void {
    this.nameDirection = direction
    this.authorDirection = null

    this.bookFilter.titleSort = new SortValue<string>(undefined, direction)
    this.bookFilter.authorSort = undefined

    this.retrieve(false)
  }

  public authorClick (direction: SortDirection): void {
    this.authorDirection = direction
    this.nameDirection = null

    this.bookFilter.titleSort = undefined
    this.bookFilter.authorSort = new SortValue<string>(undefined, direction)

    this.retrieve(false)
  }

  public knowGreenClick (select: boolean): void {
    this.knowGreen = select
    if (select) {
      this.knowYellow = false
      this.knowRed = false
      this.bookFilter.knowFilter!.lessThan = undefined
      this.bookFilter.knowFilter!.lessThanOrEqual = 1.0
      this.bookFilter.knowFilter!.greaterThanOrEqual = knowLevels[2].know
    } else {
      this.bookFilter.knowFilter!.lessThan = undefined
      this.bookFilter.knowFilter!.lessThanOrEqual = undefined
      this.bookFilter.knowFilter!.greaterThanOrEqual = undefined
    }

    if (this.bookFilter.titleSort) this.bookFilter.titleSort.maxValue = undefined
    if (this.bookFilter.authorSort) this.bookFilter.authorSort.maxValue = undefined

    this.retrieve(false)
  }

  public knowYellowClick (select: boolean): void {
    this.knowYellow = select
    if (select) {
      this.knowGreen = false
      this.knowRed = false
      this.bookFilter.knowFilter!.lessThan = knowLevels[2].know
      this.bookFilter.knowFilter!.lessThanOrEqual = undefined
      this.bookFilter.knowFilter!.greaterThanOrEqual = knowLevels[1].know
    } else {
      this.bookFilter.knowFilter!.lessThan = undefined
      this.bookFilter.knowFilter!.lessThanOrEqual = undefined
      this.bookFilter.knowFilter!.greaterThanOrEqual = undefined
    }

    if (this.bookFilter.titleSort) this.bookFilter.titleSort.maxValue = undefined
    if (this.bookFilter.authorSort) this.bookFilter.authorSort.maxValue = undefined

    this.retrieve(false)
  }

  public knowRedClick (select: boolean): void {
    this.knowRed = select
    if (select) {
      this.knowGreen = false
      this.knowYellow = false
      this.bookFilter.knowFilter!.lessThan = knowLevels[1].know
      this.bookFilter.knowFilter!.lessThanOrEqual = undefined
      this.bookFilter.knowFilter!.greaterThanOrEqual = knowLevels[0].know
    } else {
      this.bookFilter.knowFilter!.lessThan = undefined
      this.bookFilter.knowFilter!.lessThanOrEqual = undefined
      this.bookFilter.knowFilter!.greaterThanOrEqual = undefined
    }

    if (this.bookFilter.titleSort) this.bookFilter.titleSort.maxValue = undefined
    if (this.bookFilter.authorSort) this.bookFilter.authorSort.maxValue = undefined

    this.retrieve(false)
  }
}
</script>
