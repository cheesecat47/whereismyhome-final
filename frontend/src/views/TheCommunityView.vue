<template>
  <div class="bg-white py-24 sm:py-32">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="mx-auto max-w-2xl lg:mx-0">
        <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">우리 동네</h2>
        <p class="mt-2 text-lg leading-8 text-gray-600">우리 동네 커뮤니티를 이용하여 사용자들과 소통해보세요.</p>
      </div>
      <div
        class="mx-auto mt-10 grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 border-t border-gray-200 pt-10 sm:mt-16 sm:pt-16 lg:mx-0 lg:max-w-none lg:grid-cols-3"
      >
        <article
          v-for="post in posts"
          :key="post.id"
          class="flex max-w-xl flex-col items-start justify-between border-solid border-2 p-4"
        >
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
        </article>
      </div>
    </div>
  </div>
</template>

<script setup>
const posts = [
  {
    id: 1,
    title: '타이틀 입니다',
    description:
      '내용입니다 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
    date: 'Mar 16, 2020',
    datetime: '2020-03-16',
    author: {
      name: '이승현',
      role: '26',
      imageUrl: '../../public/Mask group.png',
    },
  },
  {
    id: 1,
    title: '타이틀 입니다',
    description:
      '내용입니다 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
    date: 'Mar 16, 2020',
    datetime: '2020-03-16',
    author: {
      name: '이승현',
      role: '26',
      imageUrl: '../../public/Mask group.png',
    },
  },
  {
    id: 1,
    title: '타이틀 입니다',
    description:
      '내용입니다 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
    date: 'Mar 16, 2020',
    datetime: '2020-03-16',
    author: {
      name: '이승현',
      role: '26',
      imageUrl: '../../public/Mask group.png',
    },
  },
];
</script>
