import Vue from 'vue'
import VueRouter, { Route, RouteConfig } from 'vue-router'

import { Authority } from '@/shared/authority'

import Library from '@/components/library/library.vue'
import NewBook from '@/components/newBook/newBook.vue'
import Book from '@/components/book/book.vue'
import MyDictionary from '@/components/myDictionary/myDictionary.vue'
import CardsLearn from '@/components/cardsLearn/cardsLearn.vue'
import account from '@/router/account'

Vue.use(VueRouter)

let lastPage: Route | null = null

const routes: Array<RouteConfig> = [
  {
    path: '/',
    component: Library,
    meta: {
      authorities: [Authority.NO_AUTHORITY, Authority.USER]
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
    path: '/my-dictionary',
    component: MyDictionary,
    meta: {
      authorities: [Authority.USER],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/cards-learn',
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
    name: 'NewBook',
    component: NewBook,
    meta: {
      authorities: [Authority.ADMIN],
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
