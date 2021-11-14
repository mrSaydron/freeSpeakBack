export interface GalleryItem {
  pictureUrl?: string,
  args?: Map<string, string>
}

export class GalleryItemDto implements GalleryItem {
  constructor (
    public pictureUrl?: string,
    public args?: Map<string, string>
  ) {
  }
}
