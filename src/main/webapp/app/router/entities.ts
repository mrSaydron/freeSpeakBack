import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Book = () => import('@/entities/book/book.vue');
// prettier-ignore
const BookUpdate = () => import('@/entities/book/book-update.vue');
// prettier-ignore
const BookDetails = () => import('@/entities/book/book-details.vue');
// prettier-ignore
const Dictionary = () => import('@/entities/dictionary/dictionary.vue');
// prettier-ignore
const DictionaryUpdate = () => import('@/entities/dictionary/dictionary-update.vue');
// prettier-ignore
const DictionaryDetails = () => import('@/entities/dictionary/dictionary-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/book',
    name: 'Book',
    component: Book,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/book/new',
    name: 'BookCreate',
    component: BookUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/book/:bookId/edit',
    name: 'BookEdit',
    component: BookUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/book/:bookId/view',
    name: 'BookView',
    component: BookDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/dictionary',
    name: 'Dictionary',
    component: Dictionary,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dictionary/new',
    name: 'DictionaryCreate',
    component: DictionaryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dictionary/:dictionaryId/edit',
    name: 'DictionaryEdit',
    component: DictionaryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dictionary/:dictionaryId/view',
    name: 'DictionaryView',
    component: DictionaryDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
