<template>
  <v-card
    class="ma-2"
    height="250"
    width="200"
    elevation="0"
  >
    <v-img
      :src=galleryItem.pictureUrl
      height="200"
    ></v-img>
    <v-card-title>
      {{  }}
    </v-card-title>
  </v-card>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue, Prop } from 'vue-property-decorator'
import { GalleryItemDto } from '@/model/gallery/galleryItemDto'

@Component({
  components: {}
})
export default class GalleryItem extends Vue {
  @Prop(Object) readonly galleryItem?: GalleryItemDto
  @Prop(String) readonly type?: string

}
</script>

<style scoped>

</style>
