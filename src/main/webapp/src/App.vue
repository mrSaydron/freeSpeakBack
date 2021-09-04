<template>
  <v-app>
    <Header></Header>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import Component from 'vue-class-component'
import { Vue } from 'vue-property-decorator'

import Header from '@/components/header/header'

@Component({
  components: {
    Header
  }
})
export default class App extends Vue {
}
</script>
