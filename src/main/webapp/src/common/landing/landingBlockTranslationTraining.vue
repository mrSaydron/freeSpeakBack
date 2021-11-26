<template>
  <v-container
    fluid
    style="height: 90vh"
    class="pa-0"
  >
    <v-row
      class="white"
    >
      <v-col
        align-self="center"
      >
        <v-img
          alt="Изображение: под матрицу библиотекоршей дающей выбор из красной и синей книги"
          contain
          height="85vh"
        ></v-img>
      </v-col>
      <v-col
        align-self="center"
        class="text-center"
      >
        <h2>Практикуй перевод на текстах</h2>
        <h2>сотен книг, из обшироной библиотеки</h2>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Vue } from 'vue-property-decorator'
import Component from 'vue-class-component'

@Component({
  components: {}
})
export default class LandingBlockTranslationTraining extends Vue {
}
</script>

<style scoped>

</style>
