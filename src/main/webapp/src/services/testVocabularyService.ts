import axios from 'axios'
import { TestVocabularyDto } from '@/model/testVocabulary/testVocabularyDto'

const baseApiUrl = '/api/test-vocabulary'

export default class TestVocabularyService {
  public async start (): Promise<TestVocabularyDto> {
    return new Promise<TestVocabularyDto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/start`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async answerFail (wordId: number, testVocabularyId: number): Promise<TestVocabularyDto> {
    return new Promise<TestVocabularyDto>((resolve, reject) => {
      const params = new URLSearchParams()
      params.append('wordId', wordId.toString())
      params.append('testVocabularyId', testVocabularyId.toString())
      axios
        .post(`${baseApiUrl}/answer-fail?${params.toString()}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public async answerSuccess (wordId: number, testVocabularyId: number): Promise<TestVocabularyDto> {
    return new Promise<TestVocabularyDto>((resolve, reject) => {
      const params = new URLSearchParams()
      params.append('wordId', wordId.toString())
      params.append('testVocabularyId', testVocabularyId.toString())
      axios
        .post(`${baseApiUrl}/answer-success?${params.toString()}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
