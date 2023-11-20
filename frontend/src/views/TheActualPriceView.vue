<script setup>
import { ref, onMounted } from 'vue';
import KakaoMap from '../components/actualprice/KakaoMap.vue';
import NewsComponent from '../components/actualprice/NewsComponent.vue';
import SelectMenu from '../components/common/SelectMenu.vue';
import { getSidoNames, getGugunNames, getDongNames } from '../api/house.js';

const actualprice = [
  {
    address1: '강남구 역삼동 649',
    address2: '서울특별시 강남구 강남대로94길 28',
  },
  {
    address1: '강남구 역삼동 649',
    address2: '서울특별시 강남구 강남대로94길 28',
  },
  {
    address1: '강남구 역삼동 649',
    address2: '서울특별시 강남구 강남대로94길 28',
  },
  {
    address1: '강남구 역삼동 649',
    address2: '서울특별시 강남구 강남대로94길 28',
  },
  {
    address1: '강남구 역삼동 649',
    address2: '서울특별시 강남구 강남대로94길 28',
  },
  {
    address1: '강남구 역삼동 649',
    address2: '서울특별시 강남구 강남대로94길 28',
  },
];

const options = {
  chart: {
    type: 'line', // 라인 형태 차트
    // type: 'area', // 영역 형태 차트
    // backgroundColor: '#000', // => 차트 배경 색상
    // animation: false, // => 차트 변화 업데이트 시 애니메이션 적용 여부
    // borderColor: '#EBBA95', borderWidth: 2, borderRadius: 20px // => 차트 테두리 선
    // className: 'class' // => 스타일을 위한 클래스 이름 지정 가능
    // displayErrors: false // => 차트에 직접적으로 에러 표시 여부 (기본 값 true)
    // evets: {}, // => 차트 이벤트 리스너
    height: 300, // => 차트 높이 조절
    // inverted: true, // => x축, y축 반전 여부
    // margin: 100, // => 상우하좌 마진
    // zoomType: 'y',
    // panning: true,
    // panKey: 'shift', // => 차트 줌 인/아웃 기능
    // plotBackgroundColor: {
    //   linearGradient: [0, 0, 500, 500],
    //   stops: [
    //     [0, 'rgb(255, 255, 255)'],
    //     [1, 'rgb(200, 200, 255)'],
    //   ],
    // }, // 플롯 영역 배경 색  or 그라데이션
    // plotBackgroundImage => 이미지 링크 문자열
    // plotBorderColor: '#346691',
    // plotBorderWidth: 2, // => 플롯 영역 보더 색상
    // plotShadow: true, // => 플롯 영역 그림자
    // reflow: false, // => 창 크기 조절 시 차트 영역 반응형 여부
    // scrollablePlotArea: {
    //   minWidth: 700,
    //   scrollPositionX: 1,
    // }, // => 창 크기가 작아졌을 때 스크롤 기능
    // shadow: true, // => 차트 그림자
  },
  title: {
    text: '최근 거래 추세',
  },
  xAxis: {
    categories: ['2012', '2013', '2014'],
  },
  yAxis: {
    title: {
      text: '실거래가',
    },
  },
  series: [
    {
      name: 'item1',
      data: [1, 10, 4],
    },
    {
      name: 'item2',
      data: [5, 7, 3],
    },
  ],
};

const sido = ref([]);
const gugun = ref([]);
const dong = ref([]);
const sidoItem = ref();
const gugunItem = ref();
const dongItem = ref();

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
  gugun.value = [];
  dong.value = [];

  console.log(gugun.value);
  console.log(dong.value);
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
  dong.value = [];
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

  console.log(sidoItem.value, gugunItem.value, dongItem.value);
};
</script>

<template>
  <div class="flex gap-4 mx-auto max-w-7xl py-4 items-center">
    <SelectMenu @item-click="sidoItemClick" class="w-48" :arr="sido" />
    <SelectMenu @item-click="gugunItemClick" class="w-40" :arr="gugun" />
    <SelectMenu @item-click="dongItemClick" class="w-36" :arr="dong" />
    <div class="flex gap-2 items-center">
      <input
        type="text"
        placeholder="검색어를 입력하세요"
        class="h-9 text-sm border-0 border-black/5 text-gray-700 ring-1 ring-gray-400 placeholder:text-gray-400 focus:ring-1 focus:ring-slate-700"
      />
      <div
        class="h-9 py-[1.15rem] bg-slate-600 text-white text-sm font-semibold w-12 flex justify-center items-center border border-gray-400"
      >
        검색
      </div>
    </div>
  </div>
  <div class="flex h-full overflow-y-hidden">
    <div class="basis-1/4 overflow-y-auto h-[calc(100vh-81px)] scrollbar-hide bg-slate-100">
      <div id="roadview" class="w-full h-64"></div>
      <div v-for="item in actualprice" :key="item.id">
        <div class="p-4 border-b bg-white">
          <div class="flex gap-2">
            <span
              class="inline-flex items-center rounded-md bg-gray-50 px-2 py-1 text-xs font-medium text-gray-700 ring-1 ring-inset ring-gray-500/10"
              >대표</span
            >
            <p class="font-bold">{{ item.address1 }}</p>
          </div>
          <p class="text-sm py-2">{{ item.address2 }}</p>
        </div>
      </div>
      <Highcharts class="py-3" :options="options"></Highcharts>
      <NewsComponent />
    </div>
    <div class="basis-3/4">
      <KakaoMap class="" />
    </div>
  </div>
</template>

<style scoped></style>
