<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { signupMember } from '../api/member';
import VSelectMenu from '../components/common/VSelectMenu.vue';
import { getSidoNames, getGugunNames, getDongNames } from '../api/house.js';

const router = useRouter();
const sido = ref([]);
const gugun = ref([]);
const dong = ref([]);
const sidoItem = ref();
const gugunItem = ref();
const dongItem = ref();

const userInfo = ref({
  emailAccount: '',
  emailDomain: '',
  name: '',
  password: '',
  age: '',
  sex: '',
  address: '',
});

const email_full = ref('');

const signupEvent = () => {
  userInfo.value.emailAccount = email_full.value.split('@')[0];
  userInfo.value.emailDomain = email_full.value.split('@')[1];
  userInfo.value.address = sidoItem.value + ' ' + gugunItem.value + ' ' + dongItem.value;

  console.log(userInfo.value);
  signupMember(
    userInfo.value,
    ({ data }) => {
      console.log(data);
      router.push({ name: 'login' });
      alert('회원가입이 성공적으로 이루어졌습니다.');
    },
    (error) => {
      console.log(error);
    }
  );
};

onMounted(() => {
  getSidoNames(
    ({ data }) => {
      sido.value = data.data;
    },
    (error) => {
      console.log(error);
    }
  );
});

const sidoItemClick = (sido) => {
  sidoItem.value = sido;
  getGugunNames(
    sido,
    ({ data }) => {
      gugun.value = data.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const gugunItemClick = (gugun) => {
  gugunItem.value = gugun;
  getDongNames(
    sidoItem.value,
    gugun,
    ({ data }) => {
      dong.value = data.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const dongItemClick = (dong) => {
  dongItem.value = dong;
};
</script>

<template>
  <div class="isolate bg-white px-6 py-16 lg:px-8">
    <div class="mx-auto max-w-2xl text-center">
      <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">회원가입</h2>
      <p class="mt-4 text-sm leading-8 text-gray-600">새로방문하셨나요? 방문을 환영합니다</p>
    </div>
    <form @submit.prevent="signupEvent" class="mx-auto mt-8 max-w-xl">
      <div class="grid grid-cols-1 gap-x-8 gap-y-6 sm:grid-cols-2">
        <div class="sm:col-span-2">
          <label for="email" class="block text-sm font-semibold leading-6 text-gray-900">이메일</label>
          <div class="mt-2.5">
            <input
              v-model="email_full"
              type="email"
              name="email"
              id="email"
              autocomplete="email"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
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
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
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
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div class="sm:col-span-2">
          <label for="address" class="block text-sm font-semibold leading-6 text-gray-900">주소</label>
          <div class="flex gap-2">
            <VSelectMenu @item-click="sidoItemClick" class="basis-1/4" :arr="sido" />
            <VSelectMenu @item-click="gugunItemClick" class="basis-1/4" :arr="gugun" />
            <VSelectMenu @item-click="dongItemClick" class="basis-1/4" :arr="dong" />
          </div>
          <!-- <div class="mt-2.5">
            <input
              v-model="userInfo.address"
              type="text"
              name="address"
              id="address"
              autocomplete="address"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
            />
          </div> -->
        </div>
        <div>
          <label for="name" class="block text-sm font-semibold leading-6 text-gray-900">이름</label>
          <div class="mt-2.5">
            <input
              v-model="userInfo.name"
              type="text"
              name="name"
              id="name"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        <div class="sm:col-span-2">
          <div class="flex w-full gap-2">
            <label for="age" class="block text-sm font-semibold leading-6 text-gray-900">나이</label>
            <p class="text-xs leading-6 text-gray-600">숫자만 입력하세요. Ex) 26</p>
          </div>
          <div class="mt-2.5">
            <input
              v-model="userInfo.age"
              type="text"
              name="age"
              id="age"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6"
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
                class="h-4 w-4 border-gray-300 text-teal-600 focus:ring-teal-600"
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
                class="h-4 w-4 border-gray-300 text-teal-600 focus:ring-teal-600"
              />
              <label for="female" class="block text-sm font-medium leading-6 text-gray-900">여</label>
            </div>
          </div>
        </fieldset>
      </div>
      <div class="mt-10">
        <button
          type="submit"
          class="block w-full rounded-md bg-teal-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-teal-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-teal-600"
        >
          회원가입
        </button>
      </div>
    </form>
  </div>
</template>
