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
          alt="Изображение: лист с тестом"
          contain
          height="85vh"
        ></v-img>
      </v-col>
      <v-col
        align-self="center"
        class="text-center"
      >
        <h2>Пройти тест на знание слов и</h2>
        <h2>мы подберем книгу лично для тебя</h2>
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
export default class LandingBlockTest extends Vue {
}
</script>

<style scoped>

</style>
