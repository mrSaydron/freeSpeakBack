import axios from 'axios'
import { DictionaryDto } from '@/model/dictionaryDto'

const baseApiUrl = '/api/dictionary'

export default class BookDictionaryService {
  public find (id: number): Promise<DictionaryDto> {
    return new Promise<DictionaryDto>((resolve, reject) => {
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
}
