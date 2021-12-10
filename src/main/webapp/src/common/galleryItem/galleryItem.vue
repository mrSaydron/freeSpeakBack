<template>
  <div>
    <gallery-item-book
      v-if="checkBook()"
      :gallery-item="galleryItem"
    ></gallery-item-book>
    <gallery-item-sentence
      v-if="checkSentence()"
      :gallery-item="galleryItem"
    ></gallery-item-sentence>
    <gallery-item-card-learn
      v-if="checkWordCard()"
      :gallery-item="galleryItem"
    ></gallery-item-card-learn>
    <gallery-item-see-more
      v-if="checkSeeMore()"
      :gallery-item="galleryItem"
    ></gallery-item-see-more>
    <gallery-item-see-more
      v-if="checkSeeMore()"
      :gallery-item="galleryItem"
    ></gallery-item-see-more>
    <gallery-item-user-dictionary
      v-if="checkUserDictionary()"
      :gallery-item="galleryItem"
    ></gallery-item-user-dictionary>
    <gallery-item-mark-sentence
      v-if="checkMarkSentence()"
      :gallery-item="galleryItem"
    ></gallery-item-mark-sentence>
    <gallery-item-test-vocabulary
      v-if="checkTestVocabulary()"
      :gallery-item="galleryItem"
    ></gallery-item-test-vocabulary>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Prop, Vue } from 'vue-property-decorator'
import { GalleryItemDto } from '@/model/galleryItem/galleryItemDto'
import { GalleryItemTypeEnum } from '@/model/enums/galleryItemTypeEnum'
import GalleryItemBook from '@/common/galleryItem/galleryItemBook.vue'
import GalleryItemSentence from '@/common/galleryItem/galleryItemSentence.vue'
import GalleryItemCardLearn from '@/common/galleryItem/galleryItemCardLearn.vue'
import GalleryItemSeeMore from '@/common/galleryItem/gallerytemSeeMore.vue'
import GalleryItemUserDictionary from '@/common/galleryItem/galleryItemUserDictionary.vue'
import GalleryItemMarkSentence from '@/common/galleryItem/galleryItemMarkSentence.vue'
import GalleryItemTestVocabulary from '@/common/galleryItem/galleryItemTestVocabulary.vue'

@Component({
  components: {
    GalleryItemBook,
    GalleryItemCardLearn,
    GalleryItemSentence,
    GalleryItemSeeMore,
    GalleryItemUserDictionary,
    GalleryItemMarkSentence,
    GalleryItemTestVocabulary
  }
})
export default class GalleryItem extends Vue {
  @Prop(Object) readonly galleryItem?: GalleryItemDto

  public checkBook (): boolean {
    return this.galleryItem !== undefined &&
      this.galleryItem.type === GalleryItemTypeEnum.BOOK
  }

  public checkSentence (): boolean {
    return this.galleryItem !== undefined &&
      this.galleryItem.type === GalleryItemTypeEnum.SENTENCE
  }

  public checkWordCard (): boolean {
    return this.galleryItem !== undefined &&
      this.galleryItem.type === GalleryItemTypeEnum.CARD_LEARN
  }

  public checkSeeMore (): boolean {
    return this.galleryItem !== undefined &&
      this.galleryItem.type === GalleryItemTypeEnum.SEE_MORE
  }

  public checkUserDictionary (): boolean {
    return this.galleryItem !== undefined &&
      this.galleryItem.type === GalleryItemTypeEnum.USER_DICTIONARY
  }

  public checkMarkSentence (): boolean {
    return this.galleryItem !== undefined &&
      this.galleryItem.type === GalleryItemTypeEnum.MARK_SENTENCE
  }

  public checkTestVocabulary (): boolean {
    return this.galleryItem !== undefined &&
      this.galleryItem.type === GalleryItemTypeEnum.TEST_VOCABULARY
  }
}
</script>

<style scoped>

</style>
