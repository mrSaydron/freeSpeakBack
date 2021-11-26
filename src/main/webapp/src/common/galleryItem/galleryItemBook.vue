<template>
  <v-card
    class="ma-2"
    height="200"
    width="200"
    elevation="0"
    @click="itemClick"
  >
    <v-img
      :src=galleryItem.pictureUrl
      height="200"
    ></v-img>
    <v-card-title>
      {{ galleryItem.title }}
    </v-card-title>
    <v-card-subtitle>
      {{ galleryItem.author }}
    </v-card-subtitle>
  </v-card>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Prop, Vue } from 'vue-property-decorator'
import { GalleryItemBookDto } from '@/model/galleryItem/galleryItemBookDto'

@Component({
  components: {}
})
export default class GalleryItemBook extends Vue {
  @Prop(Object) readonly galleryItem?: GalleryItemBookDto

  public itemClick (): void {
    if (this.galleryItem && this.galleryItem.id) {
      this.$router.push({ name: 'book', params: { id: this.galleryItem.id.toString() } })
    }
  }
}
</script>

<style scoped>

</style>
