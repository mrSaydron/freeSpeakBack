import axios from 'axios'
import { TextTagDto } from '@/model/textTagDto'

const baseApiUrl = '/api/text-tag'

export default class WordService {
  public get (id: number): Promise<TextTagDto> {
    return new Promise<TextTagDto>((resolve, reject) => {
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

  public async getAll (): Promise<TextTagDto[]> {
    return new Promise<TextTagDto[]>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
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

  public async create (entity: TextTagDto): Promise<number> {
    return new Promise<number>((resolve, reject) => {
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

  public update (entity: TextTagDto): Promise<void> {
    return new Promise<void>((resolve, reject) => {
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
