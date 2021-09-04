import LoginForm from '@/components/account/loginForm.vue'
import Register from '@/components/account/register.vue'
import ResetInit from '@/components/account/resetInit.vue'
import ResetFinish from '@/components/account/resetFinish.vue'
import { Authority } from '@/shared/authority'

export default [
  {
    path: '/login-form',
    component: LoginForm,
    meta: {
      authorities: [Authority.NO_AUTHORITY],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/register',
    component: Register,
    meta: {
      authorities: [Authority.NO_AUTHORITY],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/reset-init',
    component: ResetInit,
    meta: {
      authorities: [Authority.NO_AUTHORITY],
      backPage: () => {
        return '/'
      }
    }
  },
  {
    path: '/reset-finish/:key',
    props: true,
    component: ResetFinish,
    meta: {
      authorities: [Authority.NO_AUTHORITY],
      backPage: () => {
        return '/'
      }
    }
  }
]
