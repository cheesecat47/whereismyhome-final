<script setup>
import { ref, onMounted } from 'vue';
import VFooter from '../components/common/VFooter.vue';
import VYoutube from '../components/common/VYoutube.vue';
import { getNotices } from '../api/board';
import { getFavouriteAptTop5, getFavouriteDongTop5 } from '../api/stats.js';

const callouts = [
  {
    name: 'JTBC News',
    description: '좋은 아파트 분양 받고 싶다면 ‘이것’ 꼭 알아둬라',
    imageSrc: '../../public/carousel2.jpg',
    link: 'https://www.youtube.com/watch?v=LCsLHCAOWq0',
  },
  {
    name: '아파트써처',
    description: '5억대, 6억대까지 떨어진 서울 40평대 역세권 아파트 10곳',
    imageSrc: '../../public/carousel3.jpg',
    link: 'https://www.youtube.com/watch?v=q07hJMYKsxQ',
  },
  {
    name: '김작가 TV',
    description: '집값 정말 큰일 터질 겁니다, 공포의 2024년 부동산 전망',
    imageSrc: '../../public/carousel4.jpg',
    link: 'https://www.youtube.com/watch?v=1aJxwyRKJL0&t=1022s',
  },
];

const allNoticeData = ref([]);

const dongOption = ref([]);
const apartOption = ref([]);
const dongTop5 = ref([]);
const apartTop5 = ref([]);

onMounted(() => {
  getNotices(
    ({ data }) => {
      allNoticeData.value = data.data.slice(0, 4);
    },
    (error) => {
      console.log(error);
    }
  );

  // 가장 인기있는 동 옵션 설정
  getFavouriteDongTop5(
    ({ data }) => {
      console.log(data);
      dongTop5.value = data.data;
      const dongNameArr = data.data.map((item) => {
        return item['dongName'];
      });
      const viewsArr = data.data.map((item) => {
        return item.views;
      });

      for (let i = 0; i < 5; i++) {
        dongOption.value.push({
          name: dongNameArr[i],
          y: viewsArr[i],
        });
      }

      dongOption.value = {
        colors: ['rgb(19 78 74)', 'rgb(17 94 89)', 'rgb(15 118 110)', 'rgb(13 148 136)', 'rgb(20 184 166)'],
        chart: {
          type: 'pie',
        },
        credits: {
          enabled: false,
        },
        title: {
          text: '가장 인기있는 동 Top 5',
          align: 'center',
        },
        plotOptions: {
          pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
              enabled: false,
            },
            showInLegend: true,
          },
          series: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: [
              {
                enabled: true,
                distance: 20,
              },
              {
                enabled: true,
                distance: -40,
                format: '{point.percentage:.1f}%',
                style: {
                  fontSize: '0.8em',
                  textOutline: 'none',
                  opacity: 0.7,
                },
                filter: {
                  operator: '>',
                  property: 'percentage',
                  value: 10,
                },
              },
            ],
          },
        },
        series: [
          {
            name: '검색수',
            colorByPoint: true,
            data: dongOption.value,
          },
        ],
      };
    },
    (error) => {
      console.log(error);
    }
  );

  // 가장 인기있는 아파트 옵션 설정
  getFavouriteAptTop5(
    ({ data }) => {
      const aptNameArr = data.data.map((item) => {
        return item.apartmentName;
        // + ' (' + item.sidoName + ' ' + item.gugunName + ' ' + item.dongName + ')'
      });
      const viewsArr = data.data.map((item) => {
        return item.views;
      });

      for (let i = 0; i < 5; i++) {
        apartOption.value.push({
          name: aptNameArr[i],
          y: viewsArr[i],
        });
      }

      apartOption.value = {
        colors: ['#000000', '#333333', '#666666', '#999999', '#dddddd'],
        chart: {
          type: 'pie',
        },
        credits: {
          enabled: false,
        },
        title: {
          text: '가장 인기있는 아파트 Top 5',
          align: 'center',
        },
        plotOptions: {
          pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
              enabled: false,
            },
            // showInLegend: true,
          },
          series: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: [
              {
                enabled: true,
                distance: 20,
              },
              {
                enabled: true,
                distance: -40,
                format: '{point.percentage:.1f}%',
                style: {
                  fontSize: '0.8em',
                  textOutline: 'none',
                  opacity: 0.7,
                },
                filter: {
                  operator: '>',
                  property: 'percentage',
                  value: 10,
                },
              },
            ],
          },
        },
        series: [
          {
            name: '검색수',
            colorByPoint: true,
            data: apartOption.value,
          },
        ],
      };
    },
    (error) => {
      console.log(error);
    }
  );
});
</script>

<template>
  <div class="relative">
    <img src="../../public/image.png" class="w-full h-[38rem] object-cover" />
    <div
      class="isolate absolute top-1/2 left-1/2 -translate-y-1/2 -translate-x-1/2 text-white text-5xl text-center leading-normal"
    >
      <p>내가 원하는 집을 찾아보세요</p>
      <p class="text-xl text-right">#아파트 #실거래가 #커뮤니티 #주변정보</p>
      <p class="text-xl text-right">#차트 #어디 #내집</p>
    </div>
  </div>
  <div class="w-full h-full">
    <!-- 첫번째 컨텐츠 -->
    <div class="mx-auto max-w-5xl">
      <div class="py-16 sm:py-24 lg:max-w-none lg:py-24">
        <div class="flex gap-4">
          <Highcharts class="basis-1/2 h-full" :options="dongOption"></Highcharts>
          <div class="basis-1/2 flex flex-col justify-end mb-6">
            <h2 class="text-2xl font-bold tracking-tight text-gray-900">최근 7일간 통계</h2>
            <p class="mt-6 text-lg leading-8 text-gray-600">사용자가 많이 검색한 동을 확인해보세요</p>
            <ul role="list" class="grid gap-x-8 gap-y-12 sm:grid-cols-1 sm:gap-y-2 pt-4">
              <li v-for="item in dongTop5" :key="item.dongCode">
                <div class="flex items-center gap-x-6">
                  <div class="w-full flex justify-between items-center">
                    <h3 class="text-base font-semibold leading-7 tracking-tight text-gray-900">
                      {{ item.sidoName }} {{ item.gugunName }} {{ item.dongName }}
                    </h3>
                    <p class="text-sm font-semibold leading-6 text-gray-600">검색수 {{ item.views }}</p>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <!-- 두번째 컨텐츠 -->
    <div class="bg-gray-100">
      <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div class="mx-auto max-w-2xl py-16 sm:py-24 lg:max-w-none lg:py-32">
          <h2 class="text-2xl font-bold text-gray-900">추천 유튜브 ></h2>

          <div class="mt-6 space-y-12 lg:grid lg:grid-cols-3 lg:gap-x-6 lg:space-y-0">
            <div v-for="callout in callouts" :key="callout.name" class="group relative">
              <a :href="callout.link" target="_blank">
                <div
                  class="relative h-80 w-full overflow-hidden rounded-lg bg-white sm:aspect-h-1 sm:aspect-w-2 lg:aspect-h-1 lg:aspect-w-1 group-hover:opacity-75 sm:h-64"
                >
                  <img :src="callout.imageSrc" class="h-full w-full object-cover object-center" />
                </div>
                <h3 class="mt-6 text-sm text-gray-500">
                  <a :href="callout.href">
                    <span class="absolute inset-0" />
                    {{ callout.name }}
                  </a>
                </h3>
                <p class="text-base font-semibold text-gray-900">{{ callout.description }}</p>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 세번째 컨텐츠 -->
    <div class="w-full max-w-5xl mx-auto bg-white py-24 sm:py-32">
      <div class="flex gap-6">
        <div class="basis-1/2 px-6 lg:px-8 mt-24">
          <RouterLink :to="{ name: 'notice' }">
            <h2 class="text-2xl font-bold tracking-tight text-gray-900">공지사항 ></h2>
          </RouterLink>
          <p class="mt-6 text-lg leading-8 text-gray-600">
            각종 공지사항, 공고와 새로운 소식을 제공하는 공간입니다. 원활한 사이트 이용을 위해 공지사항을 확인해주세요.
          </p>
          <ul role="list" class="grid gap-x-8 gap-y-12 sm:grid-cols-1 sm:gap-y-2 pt-4">
            <li v-for="item in allNoticeData" :key="item.boardId">
              <RouterLink
                :to="{ name: 'notice-detail', params: { boardId: item.boardId } }"
                class="flex items-center gap-x-6"
              >
                <div class="w-full flex justify-between items-center">
                  <h3 class="text-base font-semibold leading-7 tracking-tight text-gray-900">{{ item.title }}</h3>
                  <p class="text-sm font-semibold leading-6 text-gray-600">{{ item.createdTime.split(' ')[0] }}</p>
                </div>
              </RouterLink>
              <!-- { name: 'notice-detail', params: { boardId: boardId } } -->
            </li>
          </ul>
        </div>
        <!-- <VYoutube width="520" height="280" /> -->
        <Highcharts class="flex-1 h-full" :options="apartOption"></Highcharts>
      </div>
    </div>

    <VFooter />
  </div>
</template>

<style scoped></style>

<!-- 
<div class="bg-gray-100">
  <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
    <div class="mx-auto max-w-2xl py-16 sm:py-24 lg:max-w-none lg:py-32">
      <h2 class="text-2xl font-bold text-gray-900">유튜브 추천 영상</h2>
      <VYoutube width="427" height="280" />
      // <div class="mt-6 space-y-12 lg:grid lg:grid-cols-3 lg:gap-x-6 lg:space-y-0">
        <div v-for="callout in callouts" :key="callout.name" class="group relative">
          <div
            class="relative h-80 w-full overflow-hidden rounded-lg bg-white sm:aspect-h-1 sm:aspect-w-2 lg:aspect-h-1 lg:aspect-w-1 group-hover:opacity-75 sm:h-64"
          >
            <img :src="callout.imageSrc" :alt="callout.imageAlt" class="h-full w-full object-cover object-center" />
          </div>
          <h3 class="mt-6 text-sm text-gray-500">
            <a :href="callout.href">
              <span class="absolute inset-0" />
              {{ callout.name }}
            </a>
          </h3>
          <p class="text-base font-semibold text-gray-900">{{ callout.description }}</p>
        </div>
      </div> //
    </div>
  </div>
</div> -->
