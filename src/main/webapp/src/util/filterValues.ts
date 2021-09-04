export class FilterValues<T> {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public filterName: string,
    public equals?: T,
    public notEquals?: T,
    public inValues?: [T],
    public notIn?: [T],
    public greaterThan?: T,
    public lessThan?: T,
    public greaterThanOrEqual?: T,
    public lessThanOrEqual?: T
  ) {
  }

  addAppends (params: URLSearchParams): void {
    for (const key in this) {
      if (key !== 'filterName' && this[key] !== undefined && this[key] !== null) {
        params.append(`${this.filterName}.${key}`, `${this[key]}`)
      }
    }
  }
}
