<template>
  <v-container>
    <v-text-field
      v-model="searchString"
      label="Название или автор (минимум три символа)"
      clearable
      prepend-inner-icon="mdi-magnify"
    ></v-text-field>
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
import { SortValue, asc } from '@/util/sortValue'
import { DefaultNamesEnum } from '@/model/enums/defaultNamesEnum'
import FileService from '@/services/fileService'

@Component({
  components: {
    BookCard
  }
})
export default class MyBooks extends Vue {
  @Inject() readonly bookService!: BookService;
  @Inject() readonly fileService!: FileService;

  public requestCount = 20
  public allElements = false
  public searchString = ''
  public books: BookDto[] = []
  public bookFilter = new BookFilter(
    undefined,
    false,
    undefined,
    new SortValue<string>(undefined, asc),
    undefined,
    this.requestCount
  )

  public async mounted () {
    await this.retrieve()
  }

  @Watch('searchString')
  public async searchChange (common: string, oldCommon: string) {
    if (common && common !== '' && common.length >= 3) {
      this.bookFilter.titleAuthorFilter = common
      await this.retrieve()
    } else if (oldCommon && oldCommon !== '' && oldCommon.length >= 3) {
      this.bookFilter.titleAuthorFilter = undefined
      await this.retrieve()
    }
  }

  public async next () {
    if (!this.allElements && this.books.length > 0) {
      const lastBook = this.books[this.books.length - 1]
      if (this.bookFilter.titleSort) {
        this.bookFilter.titleSort.maxValue = lastBook.title
      }
      await this.retrieve()
    }
  }

  public async retrieve (): Promise<void> {
    const nextBooks = await this.bookService.retrieve(this.bookFilter)
    nextBooks.forEach(book => {
      const name = book.pictureName ? book.pictureName : DefaultNamesEnum.book
      this.fileService.getUrl(name)
        .then(res => {
          book.pictureUrl = res
        })
    })
    this.books = this.books.concat(nextBooks)
    this.allElements = this.books.length < this.requestCount
  }
}
</script>
