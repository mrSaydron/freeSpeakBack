<template>
  <div>
    <galleries-page v-if="authenticated"></galleries-page>
    <landing v-else></landing>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue } from 'vue-property-decorator'
import GalleriesPage from '@/common/galleryPage/galleriesPage.vue'
import Landing from '@/common/landing/landing.vue'

@Component({
  components: {
    GalleriesPage,
    Landing
  }
})
export default class Home extends Vue {
  public get authenticated (): boolean {
    return this.$store.getters.authenticated
  }
}
</script>

<style scoped>

</style>
