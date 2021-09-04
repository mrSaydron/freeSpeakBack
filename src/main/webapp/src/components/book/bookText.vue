<template>
  <v-container style="white-space: pre-line">
    {{text}}
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Prop, Vue } from 'vue-property-decorator'

@Component({
  components: {}
})
export default class BookText extends Vue {
  @Prop(String) readonly text?: string
}
</script>
