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
          alt="Изображение: книга из которй появляются волшебкик и детектив"
          contain
          height="85vh"
          src="/picture/first.png"
        ></v-img>
      </v-col>
      <v-col
        align-self="center"
      >
        <v-row>
          <v-col class="text-center">
            <h2>Изучайте английский язык бесплатно,</h2>
            <h2>читая свои любимые книги</h2>
          </v-col>
        </v-row>
        <v-row>
          <v-col class="text-center">
            <v-btn>Зарегистрироваться</v-btn>
          </v-col>
        </v-row>
        <v-row>
          <v-col class="text-center">
            <v-btn>Войти</v-btn>
          </v-col>
        </v-row>
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
export default class LandingBlockFirst extends Vue {
}
</script>

<style scoped>

</style>
