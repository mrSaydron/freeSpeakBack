<template>
  <div v-if="authenticated">
    <v-app-bar elevation="0">
      <v-btn
        v-if="backPage"
        icon
        :to="backPage"
      >
        <v-icon>
          mdi-arrow-left
        </v-icon>
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
        to="/cards-learn"
      >
        ИЗУЧИТЬ СЛОВА
      </v-btn>
      <v-btn
        to="/my-dictionary"
      >
        СЛОВАРЬ
      </v-btn>
      <v-spacer></v-spacer>
      <user-menu></user-menu>
    </v-app-bar>
  </div>
  <div v-else>
    <v-app-bar elevation="0">
      <v-btn
        v-if="backPage"
        icon
        :to="backPage"
      >
        <v-icon>
          mdi-arrow-left
        </v-icon>
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
        text
        to="/login-form"
      >
        Войти
      </v-btn>
      <v-btn
        text
        to="/register"
      >
        Зарегистрироваться
      </v-btn>
    </v-app-bar>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue } from 'vue-property-decorator'

import BookMenu from '@/components/header/bookMenu.vue'
import DictionaryMenu from '@/components/header/dictionaryMenu.vue'
import UserMenu from '@/components/header/userMenu.vue'

@Component({
  components: {
    BookMenu,
    DictionaryMenu,
    UserMenu
  }
})
export default class Header extends Vue {
  public get authenticated (): boolean {
    return this.$store.getters.authenticated
  }

  public get backPage (): string {
    return this.$route.meta.backPage && this.$route.meta.backPage()
  }
}
</script>
