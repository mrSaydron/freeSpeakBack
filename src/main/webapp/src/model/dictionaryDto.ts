import { DictionaryWordDto } from '@/model/dictionaryWordDto'

export interface Dictionary {
  id?: number,
  baseLanguage?: string,
  targerLanguage?: string,
  dictionaryWords?: Array<DictionaryWordDto>,
}
export class DictionaryDto {
  constructor (
    public id?: number,
    public baseLanguage?: string,
    public targerLanguage?: string,
    public dictionaryWords?: Array<DictionaryWordDto>,
  ) {
  }
}
