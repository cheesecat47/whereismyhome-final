<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getArticleById } from '../../api/board.js';

const route = useRoute();

const { boardId } = route.params;
const detailData = ref({
  boardId: 0,
  content: '',
  createdTime: '',
  dongCode: '',
  hit: 1,
  memberEmail: '',
  memberId: 0,
  memberName: '',
  title: '',
  type: 0,
});

onMounted(() => {
  getArticleById(
    boardId,
    ({ data }) => {
      console.log(data);
      detailData.value = data.data;
    },
    (error) => {
      console.log(error);
    }
  );
});
</script>

<template>
  <div class="mx-auto max-w-4xl bg-white px-6 py-16 lg:px-8">
    <div class="relative">
      <!-- <img
        class="absolute -top-10 left-10"
        src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Loudspeaker.png"
        alt="Loudspeaker"
        width="75"
        height="75"
      /> -->
      <div class="mx-auto text-center pb-6 border-b">
        <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">공지사항</h2>
      </div>
    </div>
    <!-- bg-teal-600 text-white  -->
    <div class="my-6 p-4 bg-slate-100 border rounded-md font-bold">
      각종 공지사항, 공고와 새로운 소식을 제공하는 공간입니다.
    </div>
    <div class="border-y-2 border-black flex flex-col">
      <div class="my-2 p-4 px-8">
        <p class="font-bold text-2xl">{{ detailData.title }}</p>
        <div class="flex justify-between pt-2">
          <div class="flex gap-8">
            <p>작성일 {{ detailData.createdTime.split(' ')[0] }}</p>
            <p>작성자 운영자</p>
          </div>
          <p>조회수 {{ detailData.hit }}</p>
        </div>
      </div>

      <div class="border-t p-8 py-10 w-full h-96 whitespace-pre-line">
        {{ detailData.content }}
      </div>
    </div>
    <div class="mt-8 p-4 flex items-center font-bold bg-slate-100 h-11">피드백 반영</div>
    <div class="border p-4">
      <fieldset>
        <legend class="text-sm font-semibold leading-6 text-gray-900">현재 페이지의 정보에 만족하십니까?</legend>
        <div class="mt-4 flex">
          <div class="flex items-center gap-x-3">
            <input type="radio" class="h-4 w-4 border-gray-300 text-teal-600 focus:ring-teal-600" />
            <label for="male" class="block text-sm font-medium leading-6 text-gray-900">매우 만족</label>
          </div>
          <div class="flex items-center gap-x-3 ml-8">
            <input type="radio" class="h-4 w-4 border-gray-300 text-teal-600 focus:ring-teal-600" />
            <label for="female" class="block text-sm font-medium leading-6 text-gray-900">만족</label>
          </div>
          <div class="flex items-center gap-x-3 ml-8">
            <input type="radio" class="h-4 w-4 border-gray-300 text-teal-600 focus:ring-teal-600" />
            <label for="female" class="block text-sm font-medium leading-6 text-gray-900">보통</label>
          </div>
          <div class="flex items-center gap-x-3 ml-8">
            <input type="radio" class="h-4 w-4 border-gray-300 text-teal-600 focus:ring-teal-600" />
            <label for="female" class="block text-sm font-medium leading-6 text-gray-900">불만족</label>
          </div>
          <div class="flex items-center gap-x-3 ml-8">
            <input type="radio" class="h-4 w-4 border-gray-300 text-teal-600 focus:ring-teal-600" />
            <label for="female" class="block text-sm font-medium leading-6 text-gray-900">매우 불만족</label>
          </div>
        </div>
        <div class="mt-4">
          <label class="block text-sm font-semibold leading-6 text-gray-900">기타 의견을 적어주세요</label>
          <div class="mt-4 flex">
            <input
              type="text"
              class="basis-4/5 block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
            />
            <button
              type="submit"
              class="ml-4 basis-1/5 w-full block rounded-md bg-teal-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-teal-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-teal-600"
            >
              반영
            </button>
          </div>
        </div>
      </fieldset>
    </div>
  </div>
</template>

<style scoped></style>
