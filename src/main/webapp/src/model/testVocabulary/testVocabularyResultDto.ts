import { TestVocabularyDto } from '@/model/testVocabulary/testVocabularyDto'
import { TestWordResultDto } from '@/model/testVocabulary/testWordResultDto'

export class TestVocabularyResultDto implements TestVocabularyDto {
  constructor (
    public testVocabularyId?: number,
    public type?: string,
    public result?: TestWordResultDto
  ) {
  }
}
