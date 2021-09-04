<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        v-bind="attrs"
        v-on="on"
        tile
      >
        <v-icon left>mdi-file-word-box-outline</v-icon>
        СЛОВАРЬ
      </v-btn>
    </template>

    <v-list>
      <v-list-item to="/cards-learn">
        <v-list-item-title>КАРТОЧКИ</v-list-item-title>
      </v-list-item>
      <v-list-item to="/my-dictionary">
        <v-list-item-title>МОЙ СЛОВАРЬ</v-list-item-title>
      </v-list-item>
      <v-list-item to="/dictionary">
        <v-list-item-title>СЛОВАРЬ</v-list-item-title>
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
export default class DictionaryMenu extends Vue {
}
</script>

<style scoped>

</style>
