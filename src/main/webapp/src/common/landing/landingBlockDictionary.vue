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
        class="text-center"
      >
        <h2>Составляй словарь для интересной книги</h2>
        <h2>и учи слова что бы продвигаться вперед</h2>
      </v-col>
      <v-col
        align-self="center"
      >
        <v-img
          alt="Изображение: падающие буквы собирающиеся в слова"
          contain
          height="85vh"
        ></v-img>
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
export default class LandingBlockDictionary extends Vue {
}
</script>

<style scoped>

</style>
