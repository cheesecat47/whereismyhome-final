<script setup>
import { ref } from 'vue';
import { useMemberStore } from '../../stores/memberStore.js';
import { storeToRefs } from 'pinia';
import { writeArticle } from '../../api/board.js';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);
const boardInfo = ref({
  content: '',
  dongCode: '',
  memberId: 0,
  title: '',
});
console.log(userInfo.value);
const writeClick = () => {
  boardInfo.value.content = content.value;
  boardInfo.value.title = title.value;
  boardInfo.value.dongCode = userInfo.value.dongCode;
  boardInfo.value.memberId = userInfo.value.memberId;
  console.log(boardInfo.value);

  writeArticle(
    boardInfo.value,
    ({ data }) => {
      console.log(data);
      if (data.status == 201) {
        router.push({ name: 'community' });
      } else if (data.status == 401) {
        alert('로그인이 필요한 서비스입니다.');
        router.push({ name: 'login' });
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const title = ref('');
const content = ref('');
</script>

<template>
  <div class="isolate bg-white px-6 py-16 lg:px-8">
    <div class="mx-auto max-w-2xl text-center">
      <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">커뮤니티</h2>
      <p class="mt-4 text-sm leading-8 text-gray-600">우리 동네 커뮤니티를 이용하여 사용자들과 소통해보세요.</p>
    </div>
    <form @submit.prevent="writeClick" class="mx-auto mt-6 max-w-xl">
      <div class="grid grid-cols-1 gap-x-8 gap-y-6 sm:grid-cols-2">
        <div class="sm:col-span-2">
          <label for="user_id" class="block text-sm font-semibold leading-6 text-gray-900">제목</label>
          <div class="mt-2.5">
            <input
              v-model="title"
              type="text"
              name="user_id"
              id="user_id"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div class="sm:col-span-2">
          <label for="message" class="block text-sm font-semibold leading-6 text-gray-900">내용</label>
          <div class="mt-2.5">
            <textarea
              v-model="content"
              name="message"
              id="message"
              rows="8"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
      </div>
      <div class="mt-10">
        <button
          type="submit"
          class="block w-full rounded-md bg-teal-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-teal-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-teal-600"
        >
          작성하기
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
