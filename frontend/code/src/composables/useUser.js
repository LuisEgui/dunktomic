import { ref } from 'vue'

const user = ref()

export default function useUser() {
  const setUser = (userInfo) => {
    user.value = { ...userInfo }
  }

  return {
    user,
    setUser,
  }
}
