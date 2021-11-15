import { GalleryItemDto } from '@/model/gallery/galleryItemDto'

export interface Gallery {
  galleryItems?: GalleryItemDto[]
  type?: string
  title?: string
}

export class GalleryDto implements Gallery {
  constructor (
    public galleryItems: GalleryItemDto[],
    public type?: string,
    public title?: string
  ) {
  }
}
