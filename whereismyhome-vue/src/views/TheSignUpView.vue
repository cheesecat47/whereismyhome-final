<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { signupMember } from '../api/member';

const router = useRouter();
const userInfo = ref({
  user_id: '',
  name: '',
  password: '',
  age: '',
  sex: '',
  email_account: '',
  email_domain: '',
});

const email_full = ref('');

const signupEvent = () => {
  userInfo.value.email_account = email_full.value.split('@')[0];
  userInfo.value.email_domain = email_full.value.split('@')[1];
  signupMember(
    userInfo,
    ({ data }) => {
      console.log(data);
      alert('회원가입이 성공적으로 이루어졌습니다.');
      router.push({ name: 'main' });
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="isolate bg-white px-6 py-16 lg:px-8">
    <div
      class="absolute inset-x-0 top-[-10rem] -z-10 transform-gpu overflow-hidden blur-3xl sm:top-[-20rem]"
      aria-hidden="true"
    >
      <div
        class="relative left-1/2 -z-10 aspect-[1155/678] w-[36.125rem] max-w-none -translate-x-1/2 rotate-[30deg] bg-gradient-to-tr from-[#ff80b5] to-[#9089fc] opacity-30 sm:left-[calc(50%-40rem)] sm:w-[72.1875rem]"
        style="
          clip-path: polygon(
            74.1% 44.1%,
            100% 61.6%,
            97.5% 26.9%,
            85.5% 0.1%,
            80.7% 2%,
            72.5% 32.5%,
            60.2% 62.4%,
            52.4% 68.1%,
            47.5% 58.3%,
            45.2% 34.5%,
            27.5% 76.7%,
            0.1% 64.9%,
            17.9% 100%,
            27.6% 76.8%,
            76.1% 97.7%,
            74.1% 44.1%
          );
        "
      />
    </div>
    <div class="mx-auto max-w-2xl text-center">
      <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">회원가입</h2>
      <p class="mt-4 text-sm leading-8 text-gray-600">여기 뭐적을까? 음.. 오신것을 환영합니다</p>
    </div>
    <form @submit.prevent="signupEvent" class="mx-auto mt-16 max-w-xl sm:mt-20">
      <div class="grid grid-cols-1 gap-x-8 gap-y-6 sm:grid-cols-2">
        <div class="sm:col-span-2">
          <label for="user_id" class="block text-sm font-semibold leading-6 text-gray-900">아이디</label>
          <div class="mt-2.5">
            <input
              v-model="userInfo.user_id"
              type="text"
              name="user_id"
              id="user_id"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div class="sm:col-span-2">
          <label for="password" class="block text-sm font-semibold leading-6 text-gray-900">비밀번호</label>
          <div class="mt-2.5">
            <input
              v-model="userInfo.password"
              type="password"
              name="password"
              id="password"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div class="sm:col-span-2">
          <label for="passwordCheck" class="block text-sm font-semibold leading-6 text-gray-900">비밀번호 확인</label>
          <div class="mt-2.5">
            <input
              type="password"
              name="passwordCheck"
              id="passwordCheck"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div>
          <label for="name" class="block text-sm font-semibold leading-6 text-gray-900">이름</label>
          <div class="mt-2.5">
            <input
              v-model="userInfo.name"
              type="text"
              name="name"
              id="name"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div class="sm:col-span-2">
          <label for="age" class="block text-sm font-semibold leading-6 text-gray-900">나이</label>
          <p class="mt-1 text-sm leading-6 text-gray-600">숫자만 입력하세요. Ex) 26</p>
          <div class="mt-2.5">
            <input
              v-model="userInfo.age"
              type="text"
              name="age"
              id="age"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div class="sm:col-span-2">
          <label for="email" class="block text-sm font-semibold leading-6 text-gray-900">이메일</label>
          <div class="mt-2.5">
            <input
              v-model="email_full"
              type="email"
              name="email"
              id="email"
              autocomplete="email"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <fieldset>
          <legend class="text-sm font-semibold leading-6 text-gray-900">성별을 선택하세요</legend>
          <div class="mt-4 flex">
            <div class="flex items-center gap-x-3">
              <input
                v-model="userInfo.sex"
                id="male"
                value="M"
                name="sex"
                type="radio"
                class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
              />
              <label for="male" class="block text-sm font-medium leading-6 text-gray-900">남</label>
            </div>
            <div class="flex items-center gap-x-3 ml-8">
              <input
                v-model="userInfo.sex"
                id="female"
                value="F"
                name="sex"
                type="radio"
                class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
              />
              <label for="female" class="block text-sm font-medium leading-6 text-gray-900">여</label>
            </div>
          </div>
        </fieldset>
      </div>
      <div class="mt-10">
        <button
          type="submit"
          class="block w-full rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
        >
          회원가입
        </button>
      </div>
    </form>
  </div>
</template>
