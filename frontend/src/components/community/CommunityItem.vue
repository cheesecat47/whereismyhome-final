<script setup>
import { ref, onMounted, watch } from 'vue';
const props = defineProps({ post: Object });

const imgData = ref([
  `<svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="45" height="45"><mask id=":r19:" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#:r19:)"><rect width="36" height="36" fill="#f5dd9d"></rect><rect x="0" y="0" width="36" height="36" transform="translate(-3 7) rotate(87 18 18) scale(1)" fill="#92a68a" rx="36"></rect><g transform="translate(-7 3.5) rotate(-7 18 18)"><path d="M13,19 a1,0.75 0 0,0 10,0" fill="#000000"></path><rect x="12" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect><rect x="22" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect></g></g></svg>`,
  `<svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="45" height="45"><mask id=":r1b:" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#:r1b:)"><rect width="36" height="36" fill="#bcc499"></rect><rect x="0" y="0" width="36" height="36" transform="translate(-4 -4) rotate(148 18 18) scale(1.1)" fill="#7b8f8a" rx="36"></rect><g transform="translate(-4 -3) rotate(8 18 18)"><path d="M15 20c2 1 4 1 6 0" stroke="#000000" fill="none" stroke-linecap="round"></path><rect x="11" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect><rect x="23" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect></g></g></svg>`,
  `<svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="45" height="45"><mask id=":r1a:" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#:r1a:)"><rect width="36" height="36" fill="#7b8f8a"></rect><rect x="0" y="0" width="36" height="36" transform="translate(-1 -1) rotate(205 18 18) scale(1.1)" fill="#f5dd9d" rx="36"></rect><g transform="translate(-5 -3) rotate(5 18 18)"><path d="M15 20c2 1 4 1 6 0" stroke="#000000" fill="none" stroke-linecap="round"></path><rect x="14" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect><rect x="20" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect></g></g></svg>`,
  `<svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="45" height="45"><mask id=":r1j:" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#:r1j:)"><rect width="36" height="36" fill="#7b8f8a"></rect><rect x="0" y="0" width="36" height="36" transform="translate(-1 5) rotate(65 18 18) scale(1.2)" fill="#f5dd9d" rx="36"></rect><g transform="translate(-1 4) rotate(5 18 18)"><path d="M13,21 a1,0.75 0 0,0 10,0" fill="#000000"></path><rect x="14" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect><rect x="20" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect></g></g></svg>`,
  `<svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="45" height="45"><mask id=":r2v:" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#:r2v:)"><rect width="36" height="36" fill="#f5dd9d"></rect><rect x="0" y="0" width="36" height="36" transform="translate(2 6) rotate(182 18 18) scale(1.2)" fill="#92a68a" rx="36"></rect><g transform="translate(-6 6) rotate(2 18 18)"><path d="M13,21 a1,0.75 0 0,0 10,0" fill="#000000"></path><rect x="12" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect><rect x="22" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect></g></g></svg>`,
  `<svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="45" height="45"><mask id=":r1l:" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#:r1l:)"><rect width="36" height="36" fill="#bcc499"></rect><rect x="0" y="0" width="36" height="36" transform="translate(1 7) rotate(63 18 18) scale(1)" fill="#7b8f8a" rx="36"></rect><g transform="translate(-7 3.5) rotate(3 18 18)"><path d="M13,19 a1,0.75 0 0,0 10,0" fill="#000000"></path><rect x="11" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect><rect x="23" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect></g></g></svg>`,
  `<svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="45" height="45"><mask id=":r1p:" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#:r1p:)"><rect width="36" height="36" fill="#f5dd9d"></rect><rect x="0" y="0" width="36" height="36" transform="translate(-3 -3) rotate(67 18 18) scale(1.1)" fill="#92a68a" rx="36"></rect><g transform="translate(-3 -6) rotate(7 18 18)"><path d="M15 20c2 1 4 1 6 0" stroke="#000000" fill="none" stroke-linecap="round"></path><rect x="12" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect><rect x="22" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#000000"></rect></g></g></svg>`,
]);

const imgRandomIndex = ref(0);
onMounted(() => {
  imgRandomIndex.value = rand(0, 6);
});

function rand(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
</script>
<template>
  <div class="flex gap-x-4 w-full">
    <div v-html="imgData[imgRandomIndex]"></div>
    <!-- <img src="../../../../public/Mask group.png " alt="" class="h-10 w-10 rounded-full bg-gray-50" /> -->

    <div class="text-sm leading-6 w-full">
      <p class="font-semibold text-gray-900">
        {{ post.memberName }}
      </p>
      <div class="flex text-xs">
        <p class="text-gray-500">{{ post.createdTime.split(' ')[0] }}</p>
      </div>
    </div>
  </div>
  <div class="">
    <h3 class="mt-8 text-lg font-semibold leading-6 text-gray-900 group-hover:text-gray-600">
      {{ post.title }}
    </h3>
    <p class="mt-5 line-clamp-3 text-sm leading-6 text-gray-600">{{ post.content }}</p>
  </div>
</template>

<style scoped></style>
