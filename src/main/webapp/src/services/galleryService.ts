import { GalleryDto } from '@/model/gallery/galleryDto'
import axios from 'axios'

const baseApiUrl = '/api/gallery'

export default class GalleryService {
  public async get (type: string): Promise<GalleryDto> {
    const params = new URLSearchParams()
    params.append('type', type)

    return new Promise<GalleryDto>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}?${params.toString()}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
