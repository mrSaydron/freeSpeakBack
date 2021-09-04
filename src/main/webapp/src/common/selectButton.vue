<template>
  <v-btn
    text
    :color="!!this.sel ? 'primary' : 'normal'"
    @click="click"
  >
    {{ text }}
  </v-btn>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue, Prop, PropSync } from 'vue-property-decorator'

@Component({ components: {} })
export default class SelectButton extends Vue {
  @PropSync('select', { type: Boolean }) sel!: boolean
  @Prop(String) readonly text: string | undefined

  public click (): void {
    this.sel = !this.sel
  }
}
</script>
