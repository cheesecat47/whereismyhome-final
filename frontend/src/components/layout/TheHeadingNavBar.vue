<script setup>
import { ref, watch } from 'vue';
import { Dialog, DialogPanel, PopoverGroup } from '@headlessui/vue';
import { Bars3Icon, XMarkIcon } from '@heroicons/vue/24/outline';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '../../stores/memberStore.js';

const memberStore = useMemberStore();
const { isLogin, userInfo } = storeToRefs(memberStore);
// const { userLogout } = memberStore;
const loginCheck = ref(false);

watch(isLogin, () => {
  loginCheck.value = isLogin.value;
  console.log(loginCheck.value);
});

const logoutClick = () => {
  isLogin.value = false;
  userInfo.value = false;
  alert('로그아웃되었습니다');
};

const mobileMenuOpen = ref(false);
</script>

<template>
  <header class="bg-white border-b">
    <nav class="mx-auto max-w-7xl flex items-center justify-between p-5 lg:px-8" aria-label="Global">
      <div class="flex lg:flex-1">
        <RouterLink :to="{ name: 'main' }" class="flex items-center">
          <p class="text-lg pl-2 font-bold tracking-wider">WhereIsMyHome</p>
        </RouterLink>
      </div>
      <div class="flex lg:hidden">
        <button
          type="button"
          class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700"
          @click="mobileMenuOpen = true"
        >
          <span class="sr-only">Open main menu</span>
          <Bars3Icon class="h-6 w-6" aria-hidden="true" />
        </button>
      </div>
      <PopoverGroup class="hidden lg:flex lg:gap-x-12">
        <RouterLink :to="{ name: 'actualprice' }" class="text-base font-semibold leading-6 text-gray-900"
          >실거래가조회</RouterLink
        >
        <RouterLink :to="{ name: 'community' }" class="text-base font-semibold leading-6 text-gray-900"
          >커뮤니티</RouterLink
        >
        <RouterLink :to="{ name: 'notice' }" class="text-base font-semibold leading-6 text-gray-900"
          >공지사항</RouterLink
        >
      </PopoverGroup>
      <div v-if="!isLogin" class="hidden lg:flex lg:flex-1 lg:justify-end gap-4">
        <RouterLink :to="{ name: 'login' }" class="text-base font-semibold leading-6 text-gray-900">로그인</RouterLink>
        <RouterLink :to="{ name: 'signup' }" class="text-base font-semibold leading-6 text-gray-900"
          >회원가입</RouterLink
        >
      </div>
      <div v-else class="hidden lg:flex lg:flex-1 lg:justify-end gap-4">
        <RouterLink :to="{ name: 'main' }" class="text-base font-semibold leading-6 text-gray-900"
          >마이페이지</RouterLink
        >
        <RouterLink @click="logoutClick" :to="{ name: 'main' }" class="text-base font-semibold leading-6 text-gray-900"
          >로그아웃</RouterLink
        >
      </div>
    </nav>
    <Dialog as="div" class="lg:hidden" @close="mobileMenuOpen = false" :open="mobileMenuOpen">
      <div class="fixed inset-0 z-10" />
      <DialogPanel
        class="fixed inset-y-0 right-0 z-10 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10"
      >
        <div class="flex items-center justify-between">
          <!-- 이미지 넣을 공간 레이아웃 맞추기 위해 넣어놨습니다-->
          <div></div>
          <button type="button" class="-m-2.5 rounded-md p-2.5 text-gray-700" @click="mobileMenuOpen = false">
            <span class="sr-only">Close menu</span>
            <XMarkIcon class="h-6 w-6" aria-hidden="true" />
          </button>
        </div>
        <div class="mt-6 flow-root">
          <div class="-my-6 divide-y divide-gray-500/10">
            <div class="space-y-2 py-6">
              <RouterLink
                :to="{ name: 'actualprice' }"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >실거래가조회</RouterLink
              >
              <RouterLink
                :to="{ name: 'community' }"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >커뮤니티</RouterLink
              >
              <RouterLink
                :to="{ name: 'notice' }"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >공지사항</RouterLink
              >
            </div>
            <div v-if="!isLogin" class="py-6">
              <RouterLink
                :to="{ name: 'login' }"
                class="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >로그인</RouterLink
              >
              <RouterLink
                :to="{ name: 'signup' }"
                class="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >회원가입</RouterLink
              >
            </div>
            <div class="py-6">
              <RouterLink
                @click="logoutClick"
                :to="{ name: 'main' }"
                class="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >로그아웃</RouterLink
              >
            </div>
          </div>
        </div>
      </DialogPanel>
    </Dialog>
  </header>
</template>
