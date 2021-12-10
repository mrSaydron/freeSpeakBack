<template>
  <v-container v-if="galleryItems.length > 0">
    <v-row>
      <v-col>
        <h2>{{ gallery.title }}</h2>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-slide-group
          show-arrows
        >
          <v-slide-item
            v-for="(item, index) in galleryItems"
            :key="index"
          >
            <gallery-item
              :gallery-item="item"
            ></gallery-item>
          </v-slide-item>
        </v-slide-group>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue, Watch } from 'vue-property-decorator'
import { GalleryDto } from '@/model/gallery/galleryDto'
import GalleryService from '@/services/galleryService'
import GalleryItem from '@/common/galleryItem/galleryItem.vue'
import { GalleryHeadDto } from '@/model/gallery/galleryHeadDto'
import { GalleryItemDto } from '@/model/galleryItem/galleryItemDto'
import { GalleryItemTypeEnum } from '@/model/enums/galleryItemTypeEnum'
import { GalleryTypeEnum } from '@/model/enums/galleryTypeEnum'
import { GalleryItemSeeMoreDto } from '@/model/galleryItem/galleryItemSeeMoreDto'

@Component({
  components: {
    GalleryItem
  }
})
export default class GalleryBook extends Vue {
  @Inject() readonly galleryService!: GalleryService

  @Prop(Object) readonly galleryHead?: GalleryHeadDto

  private gallery: GalleryDto | null = null
  private galleryItems: GalleryItemDto[] = []

  public async mounted (): Promise<void> {
    if (this.galleryHead) {
      await this.getGallery(this.galleryHead)
    }
  }

  @Watch('galleryHead')
  public async galleryHeadChange (galleryHead: GalleryHeadDto): Promise<void> {
    await this.getGallery(galleryHead)
  }

  public async getGallery (galleryHead: GalleryHeadDto): Promise<void> {
    if (galleryHead && galleryHead.url) {
      this.gallery = await this.galleryService.get(galleryHead.url)
      if (this.gallery && this.gallery.galleryItems) {
        this.galleryItems = this.gallery.galleryItems

        if (this.galleryItems && this.galleryItems.length >= 10) {
          const seeMoreItem = new GalleryItemSeeMoreDto(
            'Больше',
            GalleryItemTypeEnum.SEE_MORE,
            'library'
          )
          this.galleryItems.push(seeMoreItem)
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
