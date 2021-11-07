<template>
  <div v-if="bookSentence && bookSentence.words">
    <span
      v-for="(word, index) in bookSentence.words"
      :key="index"
    >
      <span
        v-if="word.translateId"
        class="pre-wrap hover-span"
        @click="wordClick(word.translateId)"
      >{{word.word}}</span>
      <span
        v-if="!word.translateId"
        class="pre-wrap"
      >{{word.word}}</span>
      <span class="pre-wrap">{{word.afterWord}}</span>
    </span>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Prop, Vue, Watch } from 'vue-property-decorator'
import { BookSentenceDto } from '@/model/bookSentenceDto'

@Component({
  components: {
  }
})
export default class Sentence extends Vue {
  @Prop(Object) readonly bookSentence: BookSentenceDto | undefined

  public async wordClick (wordId: number) {
    this.$emit('word-click', wordId)
  }
}
</script>

<style scoped>
span.pre-wrap {
  white-space: pre-wrap;
}
span.hover-span:hover {
  background: #f8b8b8;
}
</style>
