import { TestVocabularyDto } from '@/model/testVocabulary/testVocabularyDto'
import { WordDto } from '@/model/wordDto'

export class TestVocabularyWordDto implements TestVocabularyDto {
  constructor (
    public testVocabularyId?: number,
    public type?: string,
    public word?: WordDto
  ) {
  }
}
