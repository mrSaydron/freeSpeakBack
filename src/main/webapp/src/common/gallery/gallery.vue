<template>
  <v-container>
    <v-row>
      <v-col>
        {{ title }}
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-slide-group
          show-arrows
        >
          <v-slide-item
            v-for="(item, index) in items"
            :key="index"
          >
            <gallery-item
              :gallery-item="item"
              :type="galleryDto.type"
            ></gallery-item>
          </v-slide-item>
        </v-slide-group>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue } from 'vue-property-decorator'
import { GalleryDto } from '@/model/gallery/galleryDto'
import GalleryService from '@/services/galleryService'
import { GalleryItemDto } from '@/model/gallery/galleryItemDto'
import GalleryItem from '@/common/gallery/galleryItem.vue'

@Component({
  components: {
    GalleryItem
  }
})
export default class Gallery extends Vue {
  @Prop(String) readonly gallery?: GalleryDto

  @Inject() readonly galleryService!: GalleryService

  private galleryDto?: GalleryDto
  private items: GalleryItemDto[] = []
  private title = ''

  public async mounted() {
    if (this.gallery && this.gallery.type) {
      this.galleryDto = await this.galleryService.get(this.gallery.type)
      this.items = this.galleryDto.galleryItems
      this.title = this.galleryDto.type ? this.galleryDto.type : ''
    }
  }
}
</script>

<style scoped>

</style>
