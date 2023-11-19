<script setup>
const notice = [
  {
    id: 1,
    title: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    description: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    datetime: '2020-03-16',
  },
  {
    id: 2,
    title: '중개회원 관리비 세분화 입력 기능 관련 안내',
    description: '중개회원 관리비 세분화 입력 기능 관련 안내',
    datetime: '2020-03-16',
  },
  {
    id: 3,
    title: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    description: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    datetime: '2020-03-16',
  },
  {
    id: 4,
    title: '중개회원 관리비 세분화 입력 기능 관련 안내',
    description: '중개회원 관리비 세분화 입력 기능 관련 안내',
    datetime: '2020-03-16',
  },
  {
    id: 5,
    title: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    description: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    datetime: '2020-03-16',
  },
  {
    id: 6,
    title: '중개회원 관리비 세분화 입력 기능 관련 안내',
    description: '중개회원 관리비 세분화 입력 기능 관련 안내',
    datetime: '2020-03-16',
  },
  {
    id: 7,
    title: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    description: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    datetime: '2020-03-16',
  },
  {
    id: 8,
    title: '중개회원 관리비 세분화 입력 기능 관련 안내',
    description: '중개회원 관리비 세분화 입력 기능 관련 안내',
    datetime: '2020-03-16',
  },
  {
    id: 9,
    title: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    description: '추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내',
    datetime: '2020-03-16',
  },
  {
    id: 10,
    title: '중개회원 관리비 세분화 입력 기능 관련 안내',
    description: '중개회원 관리비 세분화 입력 기능 관련 안내',
    datetime: '2020-03-16',
  },
];
</script>

<template>
  <div class="isolate bg-white px-6 py-16 lg:px-8">
    <div class="mx-auto max-w-2xl text-center">
      <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">공지사항</h2>
      <p class="mt-4 text-sm leading-8 text-gray-600">원활한 사이트 이용을 위해 공지사항을 확인해주세요.</p>
    </div>
    <div class="flex justify-end gap-1 mt-4 mx-auto max-w-7xl text-right font-bold">
      <p>전체</p>
      <p class="text-blue-700">{{ notice.length }}</p>
      <p>건</p>
    </div>
    <div
      class="mt-4 mx-auto flex max-w-7xl items-center justify-between p-4 text-center border-t-2 border-b border-black font-black"
    >
      <div class="basis-1/12">
        <p>번호</p>
      </div>
      <div class="text-left">
        <p>제목</p>
      </div>
      <div class="basis-1/12">
        <p>등록일</p>
      </div>
    </div>

    <div
      v-for="item in notice"
      :key="item.id"
      class="mx-auto flex max-w-7xl items-center justify-between p-5 border-b border-gray-200 text-center font-medium"
    >
      <div class="basis-1/12">
        <p>{{ item.id }}</p>
      </div>
      <div class="flex-1 text-left pl-4">
        <p>{{ item.title }}</p>
      </div>
      <div class="basis-1/12">
        <p>{{ item.datetime }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
