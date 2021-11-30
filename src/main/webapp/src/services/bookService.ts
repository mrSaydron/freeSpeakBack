import axios from 'axios'

// import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { BookCreateDto } from '@/model/bookCreateDto.ts'
import { BookDto } from '@/model/bookDto.ts'
import { BookFilter } from '@/services/filters/bookFilter'
import { BookSentenceDto } from '@/model/bookSentenceDto'

const baseApiUrl = '/api/book'

export default class BookService {
  private static REQUEST_COUNT_DEFAULT = 8

  /**
   * Возвращает книгу по ее идентификатору
   * @param id
   */
  public find (id: number): Promise<BookDto> {
    return new Promise<BookDto>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          res.data.pictureUrl = ''
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Возвращает список книг по запросу
   */
  public async retrieve (bookFilter: BookFilter): Promise<BookDto[]> {
    const params = new URLSearchParams()
    params.append('size', '' + (bookFilter.requestCount || BookService.REQUEST_COUNT_DEFAULT))
    bookFilter.addAppend(params)
    return new Promise<BookCreateDto[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}?${params.toString()}`)
        .then(res => {
          res.data.forEach((book: BookCreateDto) => { book.pictureUrl = '' })
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
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

  public async create (entity: BookCreateDto): Promise<BookCreateDto> {
    return new Promise<BookCreateDto>((resolve, reject) => {
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

  public update (entity: BookCreateDto): Promise<BookCreateDto> {
    return new Promise<BookCreateDto>((resolve, reject) => {
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

  public async findBookSentences (id: number): Promise<BookSentenceDto[]> {
    return new Promise<BookSentenceDto[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/sentence&bookId=${id}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async checkUserLibrary (bookId: number): Promise<boolean> {
    return new Promise<boolean>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/check-user-library/${bookId}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async addWordToDictionary (bookId: number): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/add-words-to-dictionary/${bookId}`)
        .then(() => {
          resolve()
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Сбрасывает отметку "для чтения" со всех книг пользователя
   */
  public async resetBookIsRead (): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/reset-book-is-read`)
        .then(() => {
          resolve()
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Устанавливает флаг "для чтения" на книгу
   */
  public async setBookIsRead (bookId: number): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/set-book-is-read/${bookId}`)
        .then(() => {
          resolve()
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
