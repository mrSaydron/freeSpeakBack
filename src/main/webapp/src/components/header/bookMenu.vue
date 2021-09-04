<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        v-bind="attrs"
        v-on="on"
        tile
      >
        <v-icon left>mdi-book-open-page-variant-outline</v-icon>
        КНИГИ
      </v-btn>
    </template>

    <v-list>
      <v-list-item to="/my-books">
        <v-list-item-title>МОИ КНИГИ</v-list-item-title>
      </v-list-item>
      <v-list-item to="/library">
        <v-list-item-title>БИБЛИОТЕКА</v-list-item-title>
      </v-list-item>
      <v-list-item to="/new-book">
        <v-list-item-title>ЗАГРУЗИТЬ</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script>
import Component from 'vue-class-component'
import { Vue } from 'vue-property-decorator'

@Component({
  components: {}
})
export default class BookMenu extends Vue {
}
</script>
