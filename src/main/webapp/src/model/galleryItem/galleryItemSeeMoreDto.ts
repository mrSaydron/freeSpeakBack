import { GalleryItemDto } from '@/model/galleryItem/galleryItemDto'

export class GalleryItemSeeMoreDto implements GalleryItemDto {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public title?: string,
    public pictureUrl?: string,
    public type?: string,
    public routeName?: string
  ) {
  }
}
