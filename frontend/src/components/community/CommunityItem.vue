<script setup>
defineProps({ post: Object });
</script>
<!-- :src="post.author.imageUrl"   -->
<template>
  <div class="relative flex items-center gap-x-4 w-full">
    <img src="../../../../public/Mask group.png " alt="" class="h-10 w-10 rounded-full bg-gray-50" />
    <div class="text-sm leading-6 w-full">
      <p class="font-semibold text-gray-900">
        {{ post.memberName }}
      </p>
      <div class="flex text-xs justify-between">
        <p class="text-gray-500">{{ post.createdTime.split(' ')[0] }}</p>
        <!-- <p class="text-gray-500">조회수 {{ post.hit }}</p> -->
      </div>
    </div>
  </div>
  <div class="group relative">
    <h3 class="mt-8 text-lg font-semibold leading-6 text-gray-900 group-hover:text-gray-600">
      {{ post.title }}
    </h3>
    <p class="mt-5 line-clamp-3 text-sm leading-6 text-gray-600">{{ post.content }}</p>
  </div>
</template>

<style scoped></style>
