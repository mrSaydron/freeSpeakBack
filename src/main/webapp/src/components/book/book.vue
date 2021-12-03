<template>
  <div>
    <v-parallax
      height="350"
      :src="book.pictureUrl"
    >
      <v-row>
        <v-col
          cols="12"
        >
          <h1>{{book.title}}</h1>
          <h4 v-if="book.author">{{book.author}}</h4>
          <h4 v-if="book.source">{{book.source}}</h4>
        </v-col>
      </v-row>
      <v-row
        align="end"
        justify="end"
      >
        <v-col
          class="text-end"
        >

          <v-tooltip
            left
            v-if="isReading"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                fab
                color="blue-grey"
                class="ma-2 white--text"
                x-large
                @click="notReading"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon dark>
                  mdi-star
                </v-icon>
              </v-btn>
            </template>
            <span>Не изучать слова этой книги</span>
          </v-tooltip>

          <v-tooltip
            left
            v-else
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                fab
                color="blue-grey"
                class="ma-2 white--text"
                x-large
                @click="reading"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon dark>
                  mdi-star-outline
                </v-icon>
              </v-btn>
            </template>
            <span>Настроить словарь для изучения слов этой книги</span>
          </v-tooltip>

        </v-col>
      </v-row>
    </v-parallax>
    <book-text
      :book-sentences-union="bookSentencesUnion"
    ></book-text>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue } from 'vue-property-decorator'
import BookService from '@/services/bookService'
import { BookDto } from '@/model/bookDto'
import BookText from '@/components/book/bookText.vue'
import BookDictionary from '@/components/book/bookDictionary.vue'
import { DictionaryDto } from '@/model/dictionaryDto'
import BookDictionaryService from '@/services/bookDictionaryService'
import FileService from '@/services/fileService'
import { BookSentenceDto } from '@/model/bookSentenceDto'
import BookSentenceService from '@/services/bookSentenceService'
import { BookSentenceUnionDto } from '@/model/bookSentenceUnionDto'

@Component({
  components: {
    BookText,
    BookDictionary
  }
})
export default class Book extends Vue {
  @Prop(String) readonly id?: string

  @Inject() readonly bookService!: BookService
  @Inject() readonly bookSentenceService!: BookSentenceService
  @Inject() readonly dictionaryService!: BookDictionaryService
  @Inject() readonly fileService!: FileService

  public book: BookDto = {}
  public dictionary: DictionaryDto = {}
  public tab = 0
  public bookSentences: BookSentenceDto[] = []
  public isReading = false
  public bookSentencesUnion: BookSentenceUnionDto | null = null

  public async mounted () {
    if (this.id) {
      const bookId = Number(this.id)

      const sentencesPromise = this.bookSentenceService.findByBook(bookId)
        .then(data => {
          this.bookSentences = data
        })
        .catch(err => console.log(err))

      const bookPromise = this.bookService.find(Number(bookId))
        .then(book => {
          this.book = book
          if (this.book.pictureId) {
            this.fileService.getUrl(this.book.pictureId)
              .then(res => {
                this.book.pictureUrl = res
              })
            this.isReading = this.book.isReading || false
          }
        })
        .catch(err => console.log(err))

      Promise.all([sentencesPromise, bookPromise])
        .then(() => {
          this.bookSentencesUnion = new BookSentenceUnionDto(this.book, this.bookSentences)
        })
    }
  }

  public notReading (): void {
    this.isReading = !this.isReading
    this.bookService.resetBookIsRead()
  }

  public reading (): void {
    if (this.book && this.book.id) {
      this.isReading = !this.isReading
      this.bookService.setBookIsRead(this.book.id)
    }
  }
}
</script>
