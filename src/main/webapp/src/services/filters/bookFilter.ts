import { asc, SortValue } from '@/util/sortValue'
import { FilterValues } from '@/util/filterValues'

export enum BookSortFields {
  Title = 'title',
  Author = 'author'
}

export class BookFilter {
  public titleAuthor = new FilterValues<string>('titleAuthor')
  public know = new FilterValues<number>('know')

  public sort = new SortValue<BookSortFields>(BookSortFields.Title, undefined, asc)

  /* eslint no-useless-constructor: "off" */
  constructor (
    public requestCount = 20
  ) {
  }

  addAppend (params: URLSearchParams): void {
    this.titleAuthor.addAppends(params)
    this.know.addAppends(params)

    if (this.sort.sortDirection) {
      params.append('sort', `${this.sort.sortField},${this.sort.sortDirection.direction}`)
      if (this.sort.maxValue) {
        params.append(`${this.sort.sortField}.${this.sort.sortDirection.compare}`, `${this.sort.maxValue}`)
      }
    }
  }
}
