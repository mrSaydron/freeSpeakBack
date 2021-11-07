import axios from 'axios'
import { BookSentenceDto } from '@/model/bookSentenceDto'
import { BookSentenceReadDto } from '@/model/bookSentenceReadDto'

const baseApiUrl = '/api/book/sentence'

export default class BookSentenceService {
  public async findByBook (id: number): Promise<BookSentenceDto[]> {
    console.log('find by book')
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
}
