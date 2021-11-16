<template>
  <v-container>
    <gallery
      v-for="(galleryHead, index) of galleryHeads"
      :key="index"
      :gallery-head="galleryHead"
    ></gallery>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import GalleriesPageService from '@/services/galleriesPageService'
import { GalleriesPageDto } from '@/model/gallery/galleriesPageDto'
import Gallery from '@/common/gallery/gallery.vue'
import { GalleryHeadDto } from '@/model/gallery/galleryHeadDto'

@Component({
  components: {
    Gallery
  }
})
export default class GalleriesPage extends Vue {
  @Inject() readonly galleriesPageService!: GalleriesPageService

  private galleriesPage: GalleriesPageDto = {}
  private galleryHeads: GalleryHeadDto[] = []

  public async mounted (): Promise<void> {
    this.galleriesPage = await this.galleriesPageService.get()
    if (this.galleriesPage.galleryHeads) {
      this.galleryHeads = this.galleriesPage.galleryHeads
    }
  }
}
</script>

<style scoped>

</style>
