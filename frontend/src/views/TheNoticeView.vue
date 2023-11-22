<script setup>
import { ref, onMounted } from 'vue';
import { getNotices } from '../api/board.js';
import VPagination from '../components/common/VPagination.vue';
import NoticeItem from '../components/notice/NoticeItem.vue';

const allNoticeData = ref([]);
const currentPage = ref(1);
const totalPage = ref(1);
const targetData = ref([]);
const onPageChange = (val) => {
  console.log(val + '번 페이지로 이동 준비 끝!!!');
  currentPage.value = val;
};

onMounted(() => {
  getNotices(
    ({ data }) => {
      allNoticeData.value = data.data;
      totalPage.value = Math.ceil(allNoticeData.value.length / 10);
      targetData.value = chunk(allNoticeData.value, 10);

      console.log(totalPage.value);
    },
    (error) => {
      console.log(error);
    }
  );
});

function chunk(data = [], size = 1) {
  const arr = [];

  for (let i = 0; i < data.length; i += size) {
    arr.push(data.slice(i, i + size));
  }

  return arr;
}
</script>

<template>
  <div class="isolate bg-white px-6 py-16 lg:px-8">
    <div class="mx-auto max-w-sm flex flex-col items-center pb-4">
      <img
        src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Animals/Dove.png"
        alt="Dove"
        width="75"
        height="75"
      />
    </div>
    <div class="mx-auto max-w-sm text-center">
      <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">공지사항</h2>
      <p class="mt-4 text-sm leading-8 text-gray-600">원활한 사이트 이용을 위해 공지사항을 확인해주세요.</p>
    </div>
    <div class="flex justify-end gap-1 mt-4 mx-auto max-w-7xl text-right font-bold">
      <p>전체</p>
      <p class="text-teal-600">{{ allNoticeData.length }}</p>
      <p>건</p>
    </div>
    <div
      class="mt-4 mx-auto flex max-w-7xl items-center justify-between p-4 text-center border-t-2 border-b border-black font-black"
    >
      <div class="basis-1/12">
        <p>번호</p>
      </div>
      <div class="flex-1 pl-4">
        <p>제목</p>
      </div>
      <div class="basis-1/12">
        <p>등록일</p>
      </div>
      <div class="basis-1/12">
        <p>조회수</p>
      </div>
    </div>
    <NoticeItem :noticeData="targetData[currentPage - 1]" />
  </div>
  <VPagination :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange" />
  <!-- <VPagination :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></VPagination> -->
</template>

<style scoped></style>
<!-- 추석, 임시공휴일, 개천절 연휴 고객센터/간추린뉴스 운영 휴무 안내 -->
