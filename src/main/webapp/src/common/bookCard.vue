<template>
  <v-col sm="3">
    <v-card @click="toBook(book.id)">
      <v-img
        :src="book.pictureUrl"
        height="200px"
      >
        <v-card-title>
          <v-icon
            x-large
            :color="knowLevel()"
            class="mr-1"
            v-if="authenticated"
          >
            mdi-head-lightbulb
          </v-icon>
          {{ book.title }}
        </v-card-title>
        <v-card-text>{{ book.author }}</v-card-text>
      </v-img>
    </v-card>
  </v-col>
</template>

<script lang="ts">
import Component from 'vue-class-component'
import { Vue, Prop } from 'vue-property-decorator'
import { BookDto } from '@/model/bookDto'
import { defaultLevel, getLevel } from '@/model/enums/knowLevelEnum'

@Component({
  components: {
  }
})
export default class BookCard extends Vue {
  @Prop(Object) readonly book: BookDto | undefined

  public toBook (id: number): void {
    this.$router.push(`/book/${id}`)
  }

  public knowLevel (): string {
    let result = defaultLevel
    if (this.book) {
      result = getLevel(this.book.know)
    }
    return result
  }

  public get authenticated (): boolean {
    return this.$store.getters.authenticated
  }
}
</script>

<style scoped>
  .shadow-text {
    text-shadow: 0 0 10px white;
    color: black;
  }
</style>
