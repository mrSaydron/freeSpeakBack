<template>
  <v-card
    class="ma-2"
    height="250"
    width="200"
    elevation="0"
    @click="itemClick"
  >
    <v-img
      height="200"
    ></v-img>
    <v-card-title>
      {{ galleryItem.title }}
    </v-card-title>
  </v-card>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Prop, Vue } from 'vue-property-decorator'
import { GalleryItemDto } from '@/model/galleryItem/galleryItemDto'

@Component({
  components: {}
})
export default class GalleryItemTestVocabulary extends Vue {
  @Prop(Object) readonly galleryItem?: GalleryItemDto

  public itemClick (): void {
    if (this.galleryItem) {
      this.$router.push({ name: 'testVocabulary' })
    }
  }
}
</script>

<style scoped>

</style>
