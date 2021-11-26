<template>
  <v-card
    class="ma-2"
    height="200"
    width="200"
    elevation="0"
    @click="itemClick"
  >
    <v-img
      src="/picture/cards.png"
      height="200"
    ></v-img>
    <v-card-title>
      {{ galleryItem.title }}
    </v-card-title>
  </v-card>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue, Prop } from 'vue-property-decorator'
import { GalleryItemDto } from '@/model/galleryItem/galleryItemDto'

@Component({
  components: {}
})
export default class GalleryItemCardLearn extends Vue {
  @Prop(Object) readonly galleryItem?: GalleryItemDto

  public itemClick (): void {
    if (this.galleryItem) {
      this.$router.push({ name: 'cardLearn' })
    }
  }
}
</script>

<style scoped>

</style>
