import Vue from 'vue'
import VueRouter, { Route, RouteConfig } from 'vue-router'

import { Authority } from '@/shared/authority'

import Library from '@/components/library/library.vue'
import NewBook from '@/components/bookCreate/bookCreate.vue'
import Book from '@/components/book/book.vue'
import UserWord from '@/components/userWord/userWord.vue'
import CardsLearn from '@/components/cardsLearn/cardsLearn.vue'
import account from '@/router/account'
import SentenceRead from '@/components/sentenceRead/sentenceRead.vue'
import Home from '@/components/home/home.vue'
import MarkSentenceRead from '@/components/sentenceReadFromMarkedBook/sentenceReadFromMarkedBook.vue'
import TestVocabulary from '@/components/testVocabulary/testVocabulary.vue'

Vue.use(VueRouter)

let lastPage: Route | null = null

const routes: Array<RouteConfig> = [
  {
    path: '/',
    component: Home,
    meta: {
      authorities: [Authority.NO_AUTHORITY, Authority.USER]
    }
  },
  {
    path: '/library',
    name: 'library',
    component: Library,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/new-book',
    component: NewBook,
    meta: {
      authorities: [Authority.ADMIN],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/book/:id',
    name: 'book',
    props: true,
    component: Book,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/user-dictionary',
    name: 'userDictionary',
    component: UserWord,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/cards-learn',
    name: 'cardLearn',
    component: CardsLearn,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/new-book',
    name: 'newBook',
    component: NewBook,
    meta: {
      authorities: [Authority.ADMIN],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/sentence-read',
    name: 'sentenceRead',
    component: SentenceRead,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/mark-sentence-read',
    name: 'markSentenceRead',
    component: MarkSentenceRead,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/test-vocabulary',
    name: 'testVocabulary',
    component: TestVocabulary,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  ...account
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  lastPage = from
  next()
})

export default router
