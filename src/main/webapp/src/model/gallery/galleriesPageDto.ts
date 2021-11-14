import { GalleryHeadDto } from '@/model/gallery/galleryHeadDto'

export interface GalleryList {
  galleryHeads?: GalleryHeadDto[]
}

export class GalleriesPageDto implements GalleryList {
  constructor (
    public galleryHeads?: GalleryHeadDto[]
  ) {
  }
}
