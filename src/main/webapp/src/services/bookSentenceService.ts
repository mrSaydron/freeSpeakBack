import axios from 'axios'
import { BookSentenceDto } from '@/model/bookSentenceDto'
import { BookSentenceReadDto } from '@/model/bookSentenceReadDto'

const baseApiUrl = '/api/book/sentence'

export default class BookSentenceService {
  /**
   * GET /api/book/sentence?bookId=${id}
   */
  public async findByBook (id: number): Promise<BookSentenceDto[]> {
    return new Promise<BookSentenceDto[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}?bookId=${id}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * GET /api/book/sentence/read
   */
  public async findToRead (): Promise<BookSentenceReadDto[]> {
    return new Promise<BookSentenceReadDto[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/read`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Пользователь перевел предложение
   * PUT /api/book/sentence/read/successful/${bookSentenceId}
   */
  public async successfulTranslate (bookSentenceId: number): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/read/successful/${bookSentenceId}`)
        .then(() => {
          resolve()
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Пользователь не смог перевести предложение
   * PUT /api/book/sentence/read/fail/${bookSentenceId}
   */
  public async failTranslate (bookSentenceId: number): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/read/fail/${bookSentenceId}`)
        .then(() => {
          resolve()
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Предложения из отмеченной книги
   * /api/book/sentence/read-marked-book
   */
  public async getSentencesFromMarkedBook (): Promise<BookSentenceReadDto[]> {
    return new Promise<BookSentenceReadDto[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/read-marked-book`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Пользователь перевел предложение из отмеченной книги
   * PUT /api/book/sentence/read/successful/${bookSentenceId}
   */
  public async successfulTranslateFromMarkedBook (bookSentenceId: number): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/read-marked-book/successful/${bookSentenceId}`)
        .then(() => {
          resolve()
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
