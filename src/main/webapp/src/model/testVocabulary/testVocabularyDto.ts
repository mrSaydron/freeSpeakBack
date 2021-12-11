export interface TestVocabulary {
  testVocabularyId?: number;
  type?: string;
}

export class TestVocabularyDto implements TestVocabulary {
  constructor (
    public testVocabularyId?: number,
    public type?: string
  ) {
  }
}
