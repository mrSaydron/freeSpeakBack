<template>
  <div>
    <gallery-daily
      v-if="checkDaily()"
      :gallery-head="galleryHead"
    ></gallery-daily>
    <gallery-book
      v-if="checkBook()"
      :gallery-head="galleryHead"
    ></gallery-book>
    <gallery-free-learn
      v-if="checkFreeLearn()"
      :gallery-head="galleryHead"
    ></gallery-free-learn>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue } from 'vue-property-decorator'
import GalleryService from '@/services/galleryService'
import { GalleryHeadDto } from '@/model/gallery/galleryHeadDto'
import GalleryBook from '@/common/gallery/galleryBook.vue'
import GalleryDaily from '@/common/gallery/galleryDaily.vue'
import { GalleryTypeEnum } from '@/model/enums/galleryTypeEnum'
import GalleryFreeLearn from '@/common/gallery/galleryFreeLearn.vue'

@Component({
  components: {
    GalleryBook,
    GalleryDaily,
    GalleryFreeLearn
  }
})
export default class Gallery extends Vue {
  @Inject() readonly galleryService!: GalleryService
  @Prop(Object) readonly galleryHead?: GalleryHeadDto

  public checkBook (): boolean {
    return this.galleryHead !== undefined &&
      this.galleryHead.type === GalleryTypeEnum.BOOK
  }

  public checkDaily (): boolean {
    return this.galleryHead !== undefined &&
      this.galleryHead.type === GalleryTypeEnum.DAILY
  }

  public checkFreeLearn (): boolean {
    return this.galleryHead !== undefined &&
      this.galleryHead.type === GalleryTypeEnum.FREE_LEARN
  }
}
</script>

<style scoped>

</style>
