import { check } from '@/services/accountService'
import { defineStore } from 'pinia'

export const useAccountStore = defineStore('account', {
    state: () => ({
        checked: false,
        loggedIn: false,
        accessToken: '',
    }),
    actions: {
        setChecked(val: boolean) {
            this.checked = val
        },
        setLoggedIn(val: boolean) {
            this.loggedIn = val
        },
        setAccessToken(token: string) {
            this.accessToken = token
        }
    }
})
