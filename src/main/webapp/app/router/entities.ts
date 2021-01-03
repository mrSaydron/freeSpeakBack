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
// prettier-ignore
const Word = () => import('@/entities/word/word.vue');
// prettier-ignore
const WordUpdate = () => import('@/entities/word/word-update.vue');
// prettier-ignore
const WordDetails = () => import('@/entities/word/word-details.vue');
// prettier-ignore
const DictionaryHasWord = () => import('@/entities/dictionary-has-word/dictionary-has-word.vue');
// prettier-ignore
const DictionaryHasWordUpdate = () => import('@/entities/dictionary-has-word/dictionary-has-word-update.vue');
// prettier-ignore
const DictionaryHasWordDetails = () => import('@/entities/dictionary-has-word/dictionary-has-word-details.vue');
// prettier-ignore
const Chat = () => import('@/entities/chat/chat.vue');
// prettier-ignore
const ChatUpdate = () => import('@/entities/chat/chat-update.vue');
// prettier-ignore
const ChatDetails = () => import('@/entities/chat/chat-details.vue');
// prettier-ignore
const Message = () => import('@/entities/message/message.vue');
// prettier-ignore
const MessageUpdate = () => import('@/entities/message/message-update.vue');
// prettier-ignore
const MessageDetails = () => import('@/entities/message/message-details.vue');
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
    path: '/word',
    name: 'Word',
    component: Word,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/word/new',
    name: 'WordCreate',
    component: WordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/word/:wordId/edit',
    name: 'WordEdit',
    component: WordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/word/:wordId/view',
    name: 'WordView',
    component: WordDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/dictionary-has-word',
    name: 'DictionaryHasWord',
    component: DictionaryHasWord,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dictionary-has-word/new',
    name: 'DictionaryHasWordCreate',
    component: DictionaryHasWordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dictionary-has-word/:dictionaryHasWordId/edit',
    name: 'DictionaryHasWordEdit',
    component: DictionaryHasWordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dictionary-has-word/:dictionaryHasWordId/view',
    name: 'DictionaryHasWordView',
    component: DictionaryHasWordDetails,
    meta: { authorities: [Authority.USER] },
  },

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
    path: '/chat',
    name: 'Chat',
    component: Chat,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/chat/new',
    name: 'ChatCreate',
    component: ChatUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/chat/:chatId/edit',
    name: 'ChatEdit',
    component: ChatUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/chat/:chatId/view',
    name: 'ChatView',
    component: ChatDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/message',
    name: 'Message',
    component: Message,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/new',
    name: 'MessageCreate',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/:messageId/edit',
    name: 'MessageEdit',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/:messageId/view',
    name: 'MessageView',
    component: MessageDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/message',
    name: 'Message',
    component: Message,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/new',
    name: 'MessageCreate',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/:messageId/edit',
    name: 'MessageEdit',
    component: MessageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/message/:messageId/view',
    name: 'MessageView',
    component: MessageDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
