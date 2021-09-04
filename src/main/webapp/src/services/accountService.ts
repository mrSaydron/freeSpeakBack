import axios from 'axios'
import { Store } from 'vuex'
import VueRouter from 'vue-router'
import { UserDto } from '@/model/userDto'
import { Authority } from '@/shared/authority'

export default class AccountService {
  // eslint-disable-next-line
  constructor (private store: Store<any>, private router: VueRouter) {
  }

  /**
   * Авторизация
   * @param login
   * @param password
   * @param remember
   */
  public async login (login: string, password: string, remember: boolean): Promise<boolean> {
    const data = { username: login, password: password, rememberMe: remember }
    try {
      const result = await axios.post('api/authenticate', data)
      await this.pushToken(result)
      return true
    } catch (err) {
      return false
    }
  }

  /**
   * Запрос токена и информации по аккаунту
   */
  public async pushToken (response: any): Promise<void> {
    const bearerToken = response.headers.authorization
    if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
      const jwt = bearerToken.slice(7, bearerToken.length)
      localStorage.setItem('jhi-authenticationToken', jwt)
    }
    await this.retrieveAccount()
  }

  /**
   * Запрашивает информацию по аккаунту пользователя
   */
  public retrieveAccount (): Promise<boolean> {
    return new Promise(resolve => {
      axios
        .get('/api/account')
        .then(response => {
          this.store.commit('authenticate')
          const account = response.data
          if (account) {
            this.store.commit('authenticated', account)
            if (sessionStorage.getItem('requested-url')) {
              const requestUrl: string = sessionStorage.getItem('requested-url') || ''
              this.router.replace(requestUrl)
              sessionStorage.removeItem('requested-url')
            }
          } else {
            this.router.push('/')
            sessionStorage.removeItem('requested-url')
          }
          resolve(true)
        })
        .catch(() => {
          this.store.commit('logout')
          resolve(false)
        })
    })
  }

  public async hasAnyAuthorityAndCheckAuth (authorities: any): Promise<boolean> {
    if (typeof authorities === 'string') {
      authorities = [authorities]
    }

    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken')

    // Нет токена, и путь для не авторизованного
    if (!token) {
      if (authorities.includes(Authority.NO_AUTHORITY)) {
        return true
      }
    }

    // Есть токен, не нет информации, запрашиваем информацию
    if ((!this.authenticated || !this.userAuthorities) && token) {
      let retrieveResult = false
      if (token) {
        retrieveResult = await this.retrieveAccount()
      }
      if (!token || !retrieveResult) {
        return false
      }
    }

    // Проверяем есть ли права
    for (let i = 0; i < authorities.length; i++) {
      if (this.userAuthorities.includes(authorities[i])) {
        return true
      }
    }

    // Нет прав
    return false
  }

  public get authenticated (): boolean {
    return this.store.getters.authenticated
  }

  public get userAuthorities (): any {
    return this.store.getters.account.authorities
  }

  /**
   * Регистрация пользователя.
   * После регистрации запрашивает токен и информацию по аккаунту
   */
  public async register (user: UserDto): Promise<boolean> {
    try {
      const result = await axios.post('/api/register', user)
      await this.pushToken(result)
    } catch (err) {
      throw err.response.data
    }
    return true
  }

  /**
   * Отправляет запрос на востановление пароля
   */
  public async resetInit (mail: string): Promise<void> {
    await axios.post('/api/account/reset-password/init', { mail: mail })
  }

  /**
   * Обновляет пароль
   */
  public async resetFinish (key: string, password: string) {
    try {
      const result = await axios.post('/api/account/reset-password/finish', { key: key, newPassword: password })
      await this.pushToken(result)
    } catch (err) {
      console.log(err.response)
    }
  }
}
