import { ref } from 'vue'

const toastMessage = ref('')
const isError = ref(false)
const isShowToast = ref(false)

export default function useToast() {
  const showToast = ({
    message = 'Ha funcionado correctamente',
    duration = 3000,
    error = false,
  }) => {
    toastMessage.value = message
    isError.value = error
    isShowToast.value = true

    setTimeout(() => {
      closeToast()
    }, duration)
  }

  const closeToast = () => {
    toastMessage.value = ''
    isShowToast.value = false
  }

  return {
    showToast,
    closeToast,
    toastMessage,
    isError,
    isShowToast,
  }
}
