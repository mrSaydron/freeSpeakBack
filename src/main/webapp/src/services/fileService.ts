import axios from 'axios'

const baseApiUrl = '/api/file'

export default class FileService {
  /**
   * Возвращает ссылку на файл
   * @param fileName
   */
  public getUrl (fileName: string): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/url?file-name=${fileName}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async savePicture (picture: File) {
    return new Promise<string>((resolve, reject) => {
      const formData = new FormData()
      formData.append('file', picture)
      axios
        .post(`${baseApiUrl}/picture`, formData)
        .then(res => { resolve(res.data) })
        .catch(err => { reject(err) })
    })
  }
}
