<script setup>
import { ref, onMounted } from 'vue';
import { getCommunityArticles } from '../api/board.js';
import VSelectMenu from '../components/common/VSelectMenu.vue';
import CommunityItem from '../components/community/CommunityItem.vue';

const communityData = ref([]);
const orderBy = ref('desc');
const searchBy = ref('');
const searchText = ref('');

onMounted(() => {
  getCommunityArticles(
    4719010300,
    null,
    null,
    null,
    ({ data }) => {
      communityData.value = data.data;
    },
    (error) => {
      console.log(error);
    }
  );
});

const orderClick = (order) => {
  orderBy.value = order == '오래된 순' ? 'asc' : 'desc';
  searchBy.value = searchBy.value == '' ? null : searchBy.value;
  searchText.value = searchText.value == '' ? null : searchText.value;
  getCommunityArticles(
    4719010300,
    searchBy.value,
    searchText.value,
    orderBy.value,
    ({ data }) => {
      console.log(data);
      communityData.value = data.data;
      console.log(communityData.value[0]);
    },
    (error) => {
      console.log(error);
    }
  );
};

const searchSelectClick = (search) => {
  searchText.value = '';
  if (search == '제목') {
    searchBy.value = 'title';
  } else if (search == '작성자') {
    searchBy.value = 'memberName';
  } else if (search == '내용') {
    searchBy.value = 'content';
  }
};

const searchButtonClick = () => {
  getCommunityArticles(
    4719010300,
    searchBy.value,
    searchText.value,
    orderBy.value,
    ({ data }) => {
      console.log(data);
      communityData.value = data.data;
      console.log(communityData.value[0]);
    },
    (error) => {
      console.log(error);
    }
  );
};

const selectView = ['최신순', '오래된 순'];
const selectSearch = ['제목', '작성자', '내용'];
</script>

<template>
  <div class="bg-white py-16">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="">
        <img
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Animals/Hatching%20Chick.png"
          alt="Hatching Chick"
          width="75"
          height="75"
        />
        <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">우리 동네</h2>
        <p class="mt-2 text-lg leading-8 text-gray-600">우리 동네 커뮤니티를 이용하여 사용자들과 소통해보세요.</p>
        <div class="flex justify-end">
          <RouterLink :to="{ name: 'community-write' }">
            <div
              class="bg-teal-600 text-white h-9 text-sm w-auto font-bold flex justify-center items-center cursor-pointer"
            >
              <i class="px-2 far fa-edit"></i>
              <span class="pr-2">작성하기</span>
            </div>
          </RouterLink>
        </div>
        <div class="flex justify-between pt-5">
          <div>
            <VSelectMenu @item-click="orderClick" :arr="selectView" />
          </div>
          <div class="flex gap-2 items-center">
            <VSelectMenu @item-click="searchSelectClick" :arr="selectSearch" />
            <input
              @keyup.enter="searchButtonClick"
              v-model="searchText"
              type="text"
              placeholder="검색어를 입력하세요"
              class="h-9 text-sm border-0 text-gray-700 ring-1 ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-slate-700"
            />
            <div
              @click="searchButtonClick"
              class="h-9 py-[1.15rem] bg-slate-600 text-white text-sm font-semibold w-12 flex justify-center items-center border border-gray-400 cursor-pointer"
            >
              검색
            </div>
          </div>
        </div>
      </div>

      <div
        class="mx-auto mt-10 grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 border-t border-gray-200 pt-10 sm:mt-10 sm:pt-16 lg:mx-0 lg:max-w-none lg:grid-cols-3"
      >
        <article v-for="post in communityData" :key="post.id" class="max-w-xl border-solid border-2 p-4">
          <CommunityItem :post="post" />
        </article>
      </div>
    </div>
  </div>
</template>
