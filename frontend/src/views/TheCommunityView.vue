<template>
  <div class="bg-white py-16">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="">
        <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">우리 동네</h2>
        <p class="mt-2 text-lg leading-8 text-gray-600">우리 동네 커뮤니티를 이용하여 사용자들과 소통해보세요.</p>
        <div class="flex justify-end">
          <div class="h-9 rounded-md text-sm w-auto font-bold flex justify-center items-center border border-gray-400">
            <i class="px-2 far fa-edit"></i>
            <span class="pr-2">작성하기</span>
          </div>
        </div>
        <div class="flex justify-between pt-5">
          <SelectMenu />
          <div class="flex gap-2 items-center">
            <input
              type="text"
              placeholder="검색어를 입력하세요"
              class="h-9 text-sm border-0 rounded-md text-gray-700 ring-1 ring-gray-400 placeholder:text-gray-400 focus:ring-1 focus:ring-slate-700"
            />
            <div
              class="h-9 py-[1.15rem] bg-slate-600 text-white rounded-md text-sm font-semibold w-12 flex justify-center items-center border border-gray-400"
            >
              검색
            </div>
          </div>
        </div>
      </div>

      <div
        class="mx-auto mt-10 grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 border-t border-gray-300 pt-10 sm:mt-10 sm:pt-16 lg:mx-0 lg:max-w-none lg:grid-cols-3"
      >
        <article
          v-for="post in posts"
          :key="post.id"
          class="flex max-w-xl flex-col items-start justify-between border-solid border-2 p-4"
        >
          <CommunityItem :post="post" />
        </article>
      </div>
    </div>
  </div>
</template>

<script setup>
import SelectMenu from '../components/common/SelectMenu.vue';
import CommunityItem from '../components/community/CommunityItem.vue';

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
