export interface GalleryItem {
  title?: string
  pictureUrl?: string
  type?: string
}

export class GalleryItemDto implements GalleryItem {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public title?: string,
    public pictureUrl?: string,
    public type?: string,
  ) {
  }
}
