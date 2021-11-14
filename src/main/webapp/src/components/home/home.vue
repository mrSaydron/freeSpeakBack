<template>
  <galleries-page></galleries-page>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue } from 'vue-property-decorator'
import GalleriesPage from '@/common/gallery/galleriesPage.vue'

@Component({
  components: {
    GalleriesPage
  }
})
export default class Home extends Vue {
}
</script>

<style scoped>

</style>
