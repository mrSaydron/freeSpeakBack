import { asc, SortValue } from '@/util/sortValue'
import { FilterValues } from '@/util/filterValues'
import loginForm from '@/components/account/loginForm.vue'

export class UserWordFilter {
  public word = new FilterValues<string>('word', 'word.word')
  public wordOrTranslate = new FilterValues<string>('wordOrTranslate')
  public boxNumber = new FilterValues<number>('boxNumber')
  public priority = new FilterValues<number>('priority', 'priority')

  public sort = new SortValue(this.word, undefined, asc)

  /* eslint no-useless-constructor: "off" */
  constructor (
    public requestCount?: number
  ) {
    this.requestCount = 20
  }

  addAppend (params: URLSearchParams): void {
    for (const key in this) {
      if (this[key] !== undefined && this[key] !== null
      ) {
        if (key === 'requestCount' && this.requestCount) {
          params.append('size', this.requestCount.toString())
        } else {
          (this[key] as any).addAppends(params)
        }
      }
    }
  }
}
