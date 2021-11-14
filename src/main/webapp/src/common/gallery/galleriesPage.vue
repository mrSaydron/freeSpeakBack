<template>
  <gallery
    v-for="(gallery, index) of galleriesPage.galleryHeads"
    :key="index"
    :gallery="gallery"
  ></gallery>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import GalleriesPageService from '@/services/galleriesPageService'
import { GalleriesPageDto } from '@/model/gallery/galleriesPageDto'
import Gallery from '@/common/gallery/gallery.vue'

@Component({ components: {
    Gallery
  }
})
export default class GalleriesPage extends Vue {
  @Inject() readonly galleriesPageService!: GalleriesPageService

  private galleriesPage: GalleriesPageDto = {}

  public async mounted (): Promise<void> {
    this.galleriesPage = await this.galleriesPageService.get()
  }
}
</script>

<style scoped>

</style>
