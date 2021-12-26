<template>
<!--  <div>text tag combobox</div>-->
  <v-combobox
    v-if="tags && tags.length > 0"
    v-model="value"
    :items="tags"
    label="Теги"
    multiple
    chips
    deletable-chips
    :item-text="itemText"
    :item-value="itemValue"
    @change="change()"
  ></v-combobox>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Inject, Prop, Vue } from 'vue-property-decorator'
import TextTagService from '@/services/textTagService'
import { TextTagDto } from '@/model/textTagDto'

@Component({
  components: {
  }
})
export default class TextTagCombobox extends Vue {
  @Inject() readonly textTagService!: TextTagService

  @Prop(Array) readonly value?: TextTagDto[]

  public tags: TextTagDto[] = []

  public async mounted () {
    this.tags = await this.textTagService.getAll()
  }

  public change () {
    this.$emit('input', this.value)
  }

  public itemText (tag: TextTagDto): string {
    return tag.tagName || ''
  }

  public itemValue (tag: TextTagDto): number {
    return tag.id || 0
  }
}
</script>

<style scoped>

</style>
