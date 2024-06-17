<script setup>
import useToast from '@/composables/useToast'

const { toastMessage, isError, isShowToast } = useToast()
</script>

<template>
  <div v-if="isShowToast" class="toast toast-top toast-end">
    <div class="alert" :class="isError ? 'alert-error' : 'alert-success'">
      <span>{{ toastMessage }}</span>
    </div>
  </div>
</template>