import axios from 'axios'
import { UserWordDto } from '@/model/userWordDto.ts'
import { UserWordFilter } from '@/services/filters/userWordFilter'
import { Card } from '@/model/card'

const baseApiUrl = '/api/user-word'

export default class UserWordService {
  private static REQUEST_COUNT_DEFAULT = 10;

  public async retrieve (filter: UserWordFilter): Promise<UserWordDto[]> {
    const params = new URLSearchParams()
    filter.addAppend(params)

    return new Promise<UserWordDto[]>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}?${params.toString()}`)
        .then(res => {
          res.data.forEach((item: UserWordDto) => UserWordDto.fill(item))
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async get (wordId: number): Promise<UserWordDto> {
    return new Promise<UserWordDto>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${wordId}`)
        .then(res => {
          const userWord = res.data
          UserWordDto.fill(userWord)
          resolve(userWord)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Добавление слова в словарь пользователя
   */
  public async addWord (wordId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/add-word/${wordId}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Сброс прогресса слова
   */
  public async eraseWord (wordId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/erase-word/${wordId}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Сброс прогресса переданных слов
   */
  public async eraseWords (wordIds: (number | undefined)[]): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/erase-words`, wordIds)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Сброс прогресса слов по условию
   */
  public async eraseAllWords (filter: UserWordFilter): Promise<any> {
    const params = new URLSearchParams()
    const knowFilter = new UserWordFilter()
    Object.assign(knowFilter, filter)
    knowFilter.requestCount = undefined
    knowFilter.sort.sortField = undefined

    knowFilter.addAppend(params)
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/erase-all-words?${params.toString()}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Отмечает слово выученным
   */
  public async knowWord (wordId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/know-word/${wordId}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Отмечает слова выученными
   */
  public async knowWords (wordIds: (number | undefined)[]): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/know-words`, wordIds)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Отмечает слова выученнми по условию
   */
  public async knowAllWords (filter: UserWordFilter): Promise<any> {
    const params = new URLSearchParams()
    const knowFilter = new UserWordFilter()
    Object.assign(knowFilter, filter)
    knowFilter.requestCount = undefined
    knowFilter.sort.sortField = undefined

    knowFilter.addAppend(params)
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/know-all-words?${params.toString()}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Удаление слова из словаря пользователя
   */
  public async removeWord (wordId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/remove-word/${wordId}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Удаление списка слов из словаря пользователя
   */
  public async removeWords (wordIds: (number | undefined)[]): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/remove-words`, wordIds)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Удаление слов из словаря пользователя по условию
   */
  public async removeAllWords (filter: UserWordFilter): Promise<any> {
    const params = new URLSearchParams()
    const knowFilter = new UserWordFilter()
    Object.assign(knowFilter, filter)
    knowFilter.requestCount = undefined
    knowFilter.sort.sortField = undefined

    knowFilter.addAppend(params)
    return new Promise<any>((resolve, reject) => {
      axios.put(`${baseApiUrl}/remove-all-words?${params.toString()}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Возвращает доступные для пользователя попытки ответов (сердца/жизни)
   */
  public async getLeftHearts (): Promise<number> {
    return new Promise<number>((resolve, reject) => {
      axios.get(`${baseApiUrl}/left-hearts`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Возвращает слова для изучения на данный день
   */
  public async getWordOfDay (): Promise<UserWordDto[]> {
    return new Promise<UserWordDto[]>((resolve, reject) => {
      axios.get(`${baseApiUrl}/words-of-day`)
        .then(res => {
          res.data.forEach((item: UserWordDto) => UserWordDto.fill(item))
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Запрашивает следующие слова на изучения из словаря пользователя
   */
  public async nextWords (): Promise<UserWordDto[]> {
    return new Promise<UserWordDto[]>((resolve, reject) => {
      axios.get(`${baseApiUrl}/next-words`)
        .then(res => {
          res.data.forEach((item: UserWordDto) => UserWordDto.fill(item))
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Пользователь не верно ответил, прогресс сбрасывается
   */
  public async answerFail (card: Card): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios.put(`${baseApiUrl}/answer-fail?userWordId=${card.id}&type=${card.wordProgress.type}`)
        .catch(err => {
          reject(err)
        })
    })
  }

  /**
   * Пользователь ответил верно, прогресс слова переходит в седующую коробку
   */
  public async answerSuccess (card: Card): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      axios.put(`${baseApiUrl}/answer-success?userWordId=${card.id}&type=${card.wordProgress.type}`)
        .catch(err => {
          reject(err)
        })
    })
  }
}
