<script setup>
defineProps({ post: Object });
</script>

<template>
  <div class="relative flex items-center gap-x-4">
    <img :src="post.author.imageUrl" alt="" class="h-10 w-10 rounded-full bg-gray-50" />
    <div class="text-sm leading-6">
      <p class="font-semibold text-gray-900">
        <a :href="post.author.href">
          <span class="absolute inset-0" />
          {{ post.author.name }}
        </a>
      </p>
      <div class="flex items-center text-xs">
        <time :datetime="post.datetime" class="text-gray-500">{{ post.date }}</time>
      </div>
    </div>
  </div>
  <div class="group relative">
    <h3 class="mt-8 text-lg font-semibold leading-6 text-gray-900 group-hover:text-gray-600">
      <a :href="post.href">
        <span class="absolute inset-0" />
        {{ post.title }}
      </a>
    </h3>
    <p class="mt-5 line-clamp-3 text-sm leading-6 text-gray-600">{{ post.description }}</p>
  </div>
</template>

<style scoped></style>
