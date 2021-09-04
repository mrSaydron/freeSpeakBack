import { Module } from 'vuex'

import { UserDto } from '@/model/userDto.js'

export const accountStore: Module<any, any> = {
  state: {
    logon: false,
    userIdentity: null,
    authenticated: false,
    ribbonOnProfiles: '',
    activeProfiles: ''
  },
  getters: {
    logon: state => state.logon,
    account: (state): UserDto => state.userIdentity,
    authenticated: state => state.authenticated,
    activeProfiles: state => state.activeProfiles,
    ribbonOnProfiles: state => state.ribbonOnProfiles
  },
  mutations: {
    authenticate (state) {
      state.logon = true
    },
    authenticated (state, identity: UserDto) {
      state.userIdentity = identity
      state.authenticated = true
      state.logon = false
    },
    logout (state) {
      localStorage.removeItem('jhi-authenticationToken')
      sessionStorage.removeItem('jhi-authenticationToken')
      state.userIdentity = null
      state.authenticated = false
      state.logon = false
    },
    setActiveProfiles (state, profile) {
      state.activeProfiles = profile
    },
    setRibbonOnProfiles (state, ribbon) {
      state.ribbonOnProfiles = ribbon
    }
  }
}
