<template>
    <v-card
      class="ma-2"
      height="250"
      width="200"
      elevation="1"
      @click="itemClick"
    >
      <v-img
        :src=galleryItem.pictureUrl
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
import { GalleryItemDto } from '@/model/gallery/galleryItemDto'
import { GalleryItemTypeEnum } from '@/model/enums/galleryItemTypeEnum'

@Component({
  components: {}
})
export default class GalleryItem extends Vue {
  @Prop(Object) readonly galleryItem?: GalleryItemDto

  public itemClick (): void {
    if (this.galleryItem && this.galleryItem.type) {
      switch (this.galleryItem.type) {
        case GalleryItemTypeEnum.WORD_CARD:
          this.$router.push({ name: 'cardLearn' })
          break
        case GalleryItemTypeEnum.SENTENCE:
          this.$router.push({ name: 'sentenceRead' })
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
