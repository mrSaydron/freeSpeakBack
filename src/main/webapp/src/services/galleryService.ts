import { GalleryDto } from '@/model/gallery/galleryDto'
import axios from 'axios'

const baseApiUrl = '/api/gallery'

export default class GalleryService {
  public async get (url: string): Promise<GalleryDto> {
    return new Promise<GalleryDto>((resolve, reject) => {
      axios
        .get(`${url}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
