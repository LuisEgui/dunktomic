import { LOCAL_STORAGE_KEYS } from "../local-storage/local-storage-constants"
import { localStorageService } from "../local-storage/local-storage-service"

export const authUserService = {
    get: function () {
        localStorageService.getItem(LOCAL_STORAGE_KEYS.AUTH)
    },

    set: function (user) {
        if (user) {
            localStorageService.setItem(LOCAL_STORAGE_KEYS.AUTH, user)
        }
    },

    remove: function () {
        localStorageService.removeItem(LOCAL_STORAGE_KEYS.AUTH)
    },

    getUserId: function () {
        const user = this.get()
        return user ? user.id : null
    }
}