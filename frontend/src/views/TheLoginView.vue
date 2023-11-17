<script setup>
import { ref } from 'vue';
import { loginMember } from '../api/member';
import { useRouter } from 'vue-router';

const router = useRouter();

const user = ref({
  user_id: '',
  password: '',
});

const loginEvent = () => {
  loginMember(
    user.value,
    ({ data }) => {
      console.log(data);
      router.push({ name: 'main' });
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">로그인</h2>
      <p class="mt-4 text-center text-sm leading-8 text-gray-600">여기 뭐적을까? 음.. 오신것을 환영합니다</p>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form v-on:submit.prevent="loginEvent" class="space-y-6">
        <div>
          <label for="email" class="block text-sm font-medium leading-6 text-gray-900">아이디</label>
          <div class="mt-2">
            <input
              v-model="user.user_id"
              type="text"
              required=""
              class="pl-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm font-medium leading-6 text-gray-900">비밀번호</label>
            <div class="text-sm">
              <a href="#" class="font-semibold text-indigo-600 hover:text-indigo-500">비밀번호를 잊어버리셨나요?</a>
            </div>
          </div>
          <div class="mt-2">
            <input
              v-model="user.password"
              id="password"
              name="password"
              type="password"
              autocomplete="current-password"
              required=""
              class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <div>
          <button
            @click="loginEvent"
            type="submit"
            class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          >
            로그인
          </button>
        </div>
      </form>

      <p class="mt-10 text-center text-sm text-gray-500">
        처음 방문하셨나요?
        {{ '  ' }}
        <RouterLink :to="{ name: 'signup' }" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500"
          >회원가입</RouterLink
        >
      </p>
    </div>
  </div>
</template>
