export interface GalleryHead {
  title?: string
  url?: string
  type?: string
  args?: Map<string, string>
}

export class GalleryHeadDto implements GalleryHead {
  constructor(
    public title?: string,
    public url?: string,
    public type?: string
  ) {
  }
}
