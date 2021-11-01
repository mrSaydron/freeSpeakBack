import { FilterValues } from '@/util/filterValues'

export class SortValue {
  constructor (
    public sortField?: FilterValues<any>,
    public maxValue?: any,
    public sortDirection?: SortDirection
  ) {
    this.maxValue = maxValue
    this.sortDirection = sortDirection
  }

  addAppends (params: URLSearchParams): void {
    if (this.sortDirection && this.sortField) {
      params.append('sort', `${this.sortField.sortName},${this.sortDirection.direction}`)
      if (this.maxValue && this.sortField) {
        params.append(`${this.sortField.filterName}.${this.sortDirection.compare}`, `${this.maxValue}`)
      }
    }
  }
}

export class SortDirection {
  constructor (
    public direction: string,
    public compare: string
  ) {
    this.direction = direction
    this.compare = compare
  }
}

export const asc: SortDirection = new SortDirection('asc', 'greaterThan')
export const desc: SortDirection = new SortDirection('desc', 'lessThan')
