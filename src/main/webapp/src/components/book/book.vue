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
          <div
            v-if="userHasAllWords != null"
          >
            <v-btn
              v-if="userHasAllWords"
              class="ma-6"
              color="green"
              disabled
            >Все слова в словаре</v-btn>
            <v-btn
              v-else
              class="ma-6"
              color="yellow"
              @click="addWordToDictionary"
            >Узучать слова</v-btn>
          </div>
        </v-col>
      </v-row>
    </v-parallax>
    <v-tabs v-model="tab">
      <v-tab>Текст</v-tab>
      <v-tab>Словарь</v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item>
        <book-text :text="book.text"></book-text>
      </v-tab-item>
      <v-tab-item>
        <book-dictionary :dictionary="dictionary"></book-dictionary>
      </v-tab-item>
    </v-tabs-items>
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
import { DefaultNamesEnum } from '@/model/enums/defaultNamesEnum'

@Component({
  components: {
    BookText,
    BookDictionary
  }
})
export default class Book extends Vue {
  @Prop(String) readonly id?: string

  @Inject() readonly bookService!: BookService
  @Inject() readonly dictionaryService!: BookDictionaryService
  @Inject() readonly fileService!: FileService

  public book: BookDto = {}
  public dictionary: DictionaryDto = {}
  public tab = 0
  public userHasAllWords: boolean | null = null

  public async mounted () {
    if (this.id) {
      const bookId = Number(this.id)
      this.bookService.sendOpenBook(bookId).catch(err => console.log(err))
      this.bookService.find(Number(bookId))
        .then(book => {
          this.book = book
          const name = this.book.pictureName ? this.book.pictureName : DefaultNamesEnum.book
          this.fileService.getUrl(name)
            .then(res => { this.book.pictureUrl = res })
          if (this.book && this.book.dictionaryId) {
            this.dictionaryService.find(this.book.dictionaryId)
              .then(dictionary => {
                this.dictionary = dictionary
              })
          }
        })
      this.bookService.checkUserLibrary(bookId)
        .then(check => {
          this.userHasAllWords = check
        })
    }
  }

  public async addWordToDictionary () {
    if (this.book && this.book.id) {
      this.bookService.addWordToDictionary(this.book.id)
      this.userHasAllWords = true
    }
  }
}
</script>
