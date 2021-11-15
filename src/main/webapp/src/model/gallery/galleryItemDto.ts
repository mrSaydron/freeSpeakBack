export interface GalleryItem {
  title?: string
  pictureUrl?: string
  type?: string
  args?: Map<string, string>
}

export class GalleryItemDto implements GalleryItem {
  constructor (
    public title?: string,
    public pictureUrl?: string,
    public type?: string,
    public args?: Map<string, string>
  ) {
  }
}
