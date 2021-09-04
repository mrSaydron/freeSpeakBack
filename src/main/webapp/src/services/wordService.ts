import axios from 'axios'
import { WordDto } from '@/model/wordDto.ts'
import { SortValue } from '@/util/sortValue'

const baseApiUrl = '/api/word'

export default class WordService {
  private static REQUEST_COUNT_DEFAULT = 10;

  public find (id: number): Promise<WordDto> {
    return new Promise<WordDto>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async retrieve (
    wordFilter?: string,
    partOfSpeechFilter?: string,
    wordSort?: SortValue<string | undefined>,
    amountSort?: SortValue<number | undefined>,
    requestCount?: number
  ): Promise<WordDto[]> {
    return new Promise<WordDto[]>((resolve, reject) => {
      axios
        .get(baseApiUrl + WordService.requestQuery(
          wordFilter,
          partOfSpeechFilter,
          wordSort,
          amountSort,
          requestCount))
        .then(res => {
          res.data.forEach((word: WordDto) => WordDto.fill(word))
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  private static requestQuery (
    wordFilter?: string,
    partOfSpeechFilter?: string,
    wordSort?: SortValue<string | undefined>,
    amountSort?: SortValue<number | undefined>,
    requestCount?: number
  ): string {
    let result = `?size=${requestCount || WordService.REQUEST_COUNT_DEFAULT}`

    if (wordFilter) {
      result += `&wordFilter.contains=${wordFilter}`
    }
    if (partOfSpeechFilter) {
      result += `&partOfSpeechFilter.contains=${partOfSpeechFilter}`
    }
    if (wordSort && wordSort.sortDirection) {
      result += `&sort=word,${wordSort.sortDirection.direction}`
      if (wordSort.maxValue) {
        result += `&startWord.${wordSort.sortDirection.compare}=${wordSort.maxValue}`
      }
    }
    if (amountSort && amountSort.sortDirection) {
      result += `&sort=totalAmount,${amountSort.sortDirection.direction}`
      if (amountSort.maxValue) {
        result += `&startAmount.${amountSort.sortDirection.compare}=${amountSort.maxValue}`
      }
    }

    return result
  }

  public delete (id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async create (entity: WordDto): Promise<WordDto> {
    return new Promise<WordDto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public update (entity: WordDto): Promise<WordDto> {
    return new Promise<WordDto>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
