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
        <h2>Выбирай, что поситать, исходя из своих предпочтений,</h2>
        <h2>или доверь нам подобрать книгу</h2>
      </v-col>
      <v-col
        align-self="center"
      >
        <v-img
          alt="Изображение: девушка на лестнице пытается дотянуться до книги"
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
export default class LandingBlockChooseWhatRead extends Vue {
}
</script>

<style scoped>

</style>
