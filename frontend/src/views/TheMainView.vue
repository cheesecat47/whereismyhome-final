<script setup>
import VYoutube from '../components/common/VYoutube.vue';
</script>

<template>
  <div class="relative">
    <img src="../../public/image.png" class="w-full h-[32rem] object-cover" />
    <div
      class="isolate absolute top-1/2 left-1/2 -translate-y-1/2 -translate-x-1/2 text-white text-5xl text-center leading-normal"
    >
      <p>내가 원하는 집을 찾아보세요</p>
      <p class="text-xl text-right">#아파트 #실거래가 #커뮤니티 #주변정보</p>
      <p class="text-xl text-right">#차트 #어디 #내집</p>
    </div>
  </div>
  <div class="w-full">
    <div class="flex justify-center mx-auto max-w-6xl pt-24 h-96">
      <div class="basis-1/2 border border-slate-200">공지사항 보러가기</div>
      <VYoutube width="427" height="284" />
      <!-- <div class="basis-1/2 bg-slate-100"></div> -->
    </div>
    <div class="flex justify-center mx-auto max-w-6xl pt-24 h-96">
      <div class="basis-1/2 border border-slate-200">공지사항 보러가기</div>
      <div class="basis-1/2 bg-slate-100"></div>
    </div>
  </div>
  <div class="w-full h-20">
    <div>© 2020 Copyright:</div>
  </div>
</template>

<style scoped></style>
