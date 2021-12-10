export interface GalleryItem {
  title?: string
  type?: string
}

export class GalleryItemDto implements GalleryItem {
  /* eslint no-useless-constructor: "off" */
  constructor (
    public title?: string,
    public type?: string,
  ) {
  }
}
