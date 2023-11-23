<script setup>
import { useRouter } from 'vue-router';

defineProps({ noticeData: Array });
const router = useRouter();

const itemClick = (boardId) => {
  router.push({ name: 'notice-detail', params: { boardId: boardId } });
};
</script>

<template>
  <div
    @click="itemClick(item.boardId)"
    v-for="(item, index) in noticeData"
    :key="index"
    class="mx-auto flex max-w-7xl items-center justify-between p-5 border-b border-gray-200 text-center cursor-pointer"
  >
    <div class="basis-1/12 text-teal-700 font-bold">
      <p>{{ item.boardId }}</p>
    </div>
    <div class="flex-1 text-left pl-4">
      <p>{{ item.title }}</p>
    </div>
    <div class="basis-1/12">
      <p>{{ item.createdTime.split(' ')[0] }}</p>
    </div>
    <div class="basis-1/12">
      <p>{{ item.hit }}</p>
    </div>
  </div>
</template>

<style scoped></style>
