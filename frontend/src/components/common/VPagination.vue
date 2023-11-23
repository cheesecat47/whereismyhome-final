<script setup>
import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/vue/20/solid';

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(['pageChange']);

function onPageChange(pg) {
  if (pg > 0 && pg <= props.totalPage) {
    window.scrollTo(0, 0);
    emit('pageChange', pg);
  }
}
</script>

<template>
  <div class="flex justify-center -my-10 mb-6">
    <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
      <div
        @click="onPageChange(currentPage - 1)"
        class="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
      >
        <ChevronLeftIcon class="h-5 w-5" aria-hidden="true" />
      </div>
      <div v-for="i in totalPage" :key="i">
        <div
          @click="onPageChange(i)"
          v-if="i == currentPage"
          class="relative z-10 inline-flex items-center bg-teal-600 px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-teal-600"
        >
          {{ i }}
        </div>
        <div
          @click="onPageChange(i)"
          v-else
          class="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 md:inline-flex"
        >
          {{ i }}
        </div>
      </div>
      <div
        @click="onPageChange(currentPage + 1)"
        class="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
      >
        <ChevronRightIcon class="h-5 w-5" aria-hidden="true" />
      </div>
    </nav>
  </div>
</template>
