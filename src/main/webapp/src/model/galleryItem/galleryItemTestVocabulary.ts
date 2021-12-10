import { GalleryItemDto } from '@/model/galleryItem/galleryItemDto'

export class GalleryItemTestVocabularyDto implements GalleryItemDto {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public title?: string,
    public type?: string
  ) {
  }
}
