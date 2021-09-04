<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="dictionary.dictionaryWords"
      :items-per-page="99999"
      :sort-by="['word.word']"
      hide-default-footer
    ></v-data-table>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Prop, Vue } from 'vue-property-decorator'
import { DictionaryDto } from '@/model/dictionaryDto'

@Component({
  components: {}
})
export default class BookDictionary extends Vue {
  @Prop(Object) readonly dictionary?: DictionaryDto

  public headers = [
    {
      text: 'Слово',
      value: 'word.word'
    },
    {
      text: 'Количество',
      value: 'count'
    }
  ]
}
</script>
