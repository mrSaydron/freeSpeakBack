import { GalleryItemDto } from '@/model/galleryItem/galleryItemDto'

export class GalleryItemBookDto implements GalleryItemDto {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public title?: string,
    public pictureUrl?: string,
    public type?: string,
    public author?: string,
    public id?: number,
    public know?: number,
    public isReading?: boolean
  ) {
  }
}
