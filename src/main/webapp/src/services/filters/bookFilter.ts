import { SortValue } from '@/util/sortValue'
import { FilterValues } from '@/util/filterValues'

export class BookFilter {
  constructor (
    public titleAuthorFilter?: string,
    public orPublicBookFilter?: boolean,
    public knowFilter?: FilterValues<number>,

    public titleSort?: SortValue<string>,
    public authorSort?: SortValue<string>,

    public requestCount?: number
  ) {
    this.requestCount = requestCount || 20
    this.knowFilter = knowFilter || new FilterValues<number>('knowFilter')
  }

  addAppend (params: URLSearchParams): void {
    if (this.titleAuthorFilter) {
      params.append('titleAuthorFilter.contains', this.titleAuthorFilter)
    }
    if (this.orPublicBookFilter) {
      params.append('orPublicBookFilter.equals', this.orPublicBookFilter + '')
    }
    if (this.knowFilter) {
      this.knowFilter.addAppends(params)
    }
  }
}
