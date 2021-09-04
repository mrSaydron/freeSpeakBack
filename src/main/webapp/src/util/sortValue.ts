export class SortValue<T> {
  constructor (
    public maxValue?: T,
    public sortDirection?: SortDirection
  ) {
    this.maxValue = maxValue
    this.sortDirection = sortDirection
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
