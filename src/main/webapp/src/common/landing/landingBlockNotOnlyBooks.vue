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
        <h2>Практикуй перевод не только на книгах,</h2>
        <h2>но и на песнях, статьях и</h2>
        <h2>новостях со всего мира</h2>
      </v-col>
      <v-col
        align-self="center"
      >
        <v-img
          alt="Изображение: икона ноты, википедии и новостей"
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
export default class LandingBlockNotOnlyBooks extends Vue {
}
</script>

<style scoped>

</style>
