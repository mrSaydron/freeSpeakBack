import Vue from 'vue'
import Vuex from 'vuex'

import { accountStore } from '@/store/modules/accountStore'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    accountStore
  }
})
