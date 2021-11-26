<template>
  <v-container fluid class="pa-0">
    <landing-block-first></landing-block-first>
    <landing-block-dictionary></landing-block-dictionary>
    <landing-block-translation-training></landing-block-translation-training>
    <landing-block-choose-what-read></landing-block-choose-what-read>
    <landing-block-test></landing-block-test>
    <landing-block-not-only-books></landing-block-not-only-books>
  </v-container>
</template>

<script lang="ts">
import { Vue } from 'vue-property-decorator'
import Component from 'vue-class-component'
import LandingBlockFirst from '@/common/landing/landingBlockFirst.vue'
import LandingBlockDictionary from '@/common/landing/landingBlockDictionary.vue'
import LandingBlockTranslationTraining from '@/common/landing/landingBlockTranslationTraining.vue'
import LandingBlockChooseWhatRead from '@/common/landing/landingBlockChooseWhatRead.vue'
import LandingBlockTest from '@/common/landing/landingBlockTest.vue'
import LandingBlockNotOnlyBooks from '@/common/landing/landingBlockNotOnlyBooks.vue'

@Component({
  components: {
    LandingBlockFirst,
    LandingBlockDictionary,
    LandingBlockTranslationTraining,
    LandingBlockChooseWhatRead,
    LandingBlockTest,
    LandingBlockNotOnlyBooks
  }
})
export default class Landing extends Vue {
}
</script>

<style scoped>

</style>
