<template>
  <v-form
    v-model="valid"
    lazy-validation
    ref="form"
  >
    <v-container>
      <v-row>
        <v-col cols="3">
          <v-img
            :src="book.pictureUrl"
            height="300"
            @click="uploadPicture"
          ></v-img>
        </v-col>
        <v-col cols="9">
          <v-btn
            :disabled="!valid"
            @click="save"
          >Сохранить</v-btn>
          <v-menu
            bottom
            left
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                icon
                v-bind="attrs"
                v-on="on"
              >
                <v-icon>mdi-dots-vertical</v-icon>
              </v-btn>
            </template>

            <v-list>
              <v-list-item @click="uploadPicture">
                <v-list-item-title>Добавить картинку</v-list-item-title>
              </v-list-item>
              <v-list-item @click="loadText">
                <v-list-item-title>Добавить текст</v-list-item-title>
              </v-list-item>
              <v-list-item @click="clearText">
                <v-list-item-title>Очистить текст</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-text-field
            v-model="book.title"
            label="Название книги"
            required
            :rules="titleRules"
          ></v-text-field>
          <v-text-field
            v-model="book.author"
            label="Автор"
          ></v-text-field>
          <v-text-field
            v-model="book.source"
            label="Источник"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="mt-3">
        <v-textarea
          ref="bookText"
          name="bookText"
          label="Текст книги"
          auto-grow
          v-model="book.text"
          :rules="textRules"
        ></v-textarea>
      </v-row>
    </v-container>
    <input
      ref="inputFile"
      type="file"
      style="display: none"
      accept="image/*"
      @change="onPictureChange"
    >
  </v-form>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import BookService from '@/services/bookService'
import { BookDto } from '@/model/bookDto'
import FileService from '@/services/fileService'
import { DefaultNamesEnum } from '@/model/enums/defaultNamesEnum'

@Component({
  components: {
  }
})
export default class Library extends Vue {
  @Inject() readonly bookService!: BookService
  @Inject() readonly fileService!: FileService

  public book: BookDto = new BookDto()
  public valid = false
  public picture: File | undefined

  public async mounted () {
    this.book.pictureUrl = await this.fileService.getUrl(DefaultNamesEnum.book)
  }

  public titleRules = [
    (v: string): boolean | string => !!v || 'Название книги обязательно к заполнению',
    (v: string): boolean | string => (v && v.length >= 3) || 'Название книги не может быть меньше трех символов'
  ];

  public textRules = [
    (v: string): boolean | string => !!v || 'Необходимо ввести текст для книги',
    (v: string): boolean | string => (v && v.length >= 1) || 'В книге должен быть хотя бы один символ'
  ];

  public clearText () {
    this.book.text = ''
  }

  public loadText () {
    const fileModal: any = this.$refs.inputLoadText
    fileModal.click()
  }

  public onTextChange (event: any) {
    const file: File = event.target.files[0]
    const reader = new FileReader()
    reader.readAsText(file)
    reader.onload = () => {
      this.book.text = reader.result
    }
  }

  public async save () {
    const form: any = this.$refs.form
    if (form.validate()) {
      if (this.picture) {
        this.book.pictureName = await this.fileService.savePicture(this.picture)
      }
      this.book.publicBook = true
      await this.bookService.create(this.book)
      this.$router.push('/library')
    }
  }

  /**
   * Открывает окно загрузки файла
   */
  public uploadPicture () {
    (this.$refs.inputFile as Vue & { click: () => void }).click()

    // this.$refs.inputFile.click()
  }

  /**
   * Сохраняет файл для загрузки, отображает картинку на странице
   */
  public async onPictureChange (event: any) {
    this.picture = event.target.files[0]
    if (this.picture) {
      const fileReader = new FileReader()
      fileReader.readAsDataURL(this.picture)
      fileReader.onload = (e) => {
        this.book.pictureUrl = String((e && e.target && e.target.result) || '')
      }
    }
  }
}
</script>
