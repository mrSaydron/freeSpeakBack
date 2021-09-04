import { setupAxiosInterceptors } from '@/config/axiosInterceptor'

export function initVueApp (vue: any) {
  setupAxiosInterceptors(() => console.log('Unauthorized!'))
}
