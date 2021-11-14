import axios from 'axios'
import { GalleriesPageDto } from '@/model/gallery/galleriesPageDto'

const baseApiUrl = '/api/galleries-page'

export default class GalleriesPageService {
  public async get (): Promise<GalleriesPageDto> {
    return new Promise<GalleriesPageDto>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
