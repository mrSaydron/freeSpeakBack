<template>
  <v-btn
    text
    :color="!!this.dir ? 'primary' : 'normal'"
    @click="sort"
  >
    <v-icon v-if="isAsc">mdi-sort-reverse-variant</v-icon>
    <v-icon v-if="isDesc">mdi-sort-variant</v-icon>
    {{ text }}
  </v-btn>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue, Prop, PropSync } from 'vue-property-decorator'
import { asc, desc, SortDirection } from '@/util/sortValue'

@Component({
  components: {
  }
})
export default class SortButton extends Vue {
  @PropSync('direction', { type: Object }) dir!: SortDirection
  @Prop(String) readonly text: string | undefined

  public sort (): void {
    if (!this.dir) {
      this.dir = asc
    } else {
      if (this.dir === asc) {
        this.dir = desc
      } else {
        this.dir = asc
      }
    }
  }

  get isAsc (): boolean {
    return !this.dir || this.dir === asc
  }

  get isDesc (): boolean {
    return this.dir && this.dir === desc
  }
}
</script>
