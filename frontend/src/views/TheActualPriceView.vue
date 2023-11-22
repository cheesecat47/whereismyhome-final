<script setup>
import { ref, onMounted } from 'vue';
import { XMarkIcon } from '@heroicons/vue/24/outline';
import vClickOutside from 'v-click-outside';
import KakaoMap from '../components/actualprice/KakaoMap.vue';
import NewsComponent from '../components/actualprice/NewsComponent.vue';
import ApartmentListItem from '../components/actualprice/ApartmentListItem.vue';
import VSelectMenu from '../components/common/VSelectMenu.vue';
import {
  getSidoNames,
  getGugunNames,
  getDongNames,
  getDongCodeByDongName,
  getHouseInfoByDongCode,
  getHouseDealsByAptCodeYearMonth,
} from '../api/house.js';
import RoadView from '../components/actualprice/RoadView.vue';
import { onClickOutside } from '@vueuse/core';

// 차트 api options
const options = {
  chart: {
    type: 'line', // 라인 형태 차트
    height: 300, // => 차트 높이 조절
    // column
    // type: 'area', // 영역 형태 차트
    // inverted: true, // => x축, y축 반전 여부
    // margin: 100, // => 상우하좌 마진
    // zoomType: 'y',
    // panning: true,
    // plotBackgroundColor: {
    //   linearGradient: [0, 0, 500, 500],
    //   stops: [
    //     [0, 'rgb(255, 255, 255)'],
    //     [1, 'rgb(200, 200, 255)'],
    //   ],
    // }, // 플롯 영역 배경 색  or 그라데이션
    // shadow: true, // => 차트 그림자
  },
  credits: {
    enabled: false,
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

// 바깥영역 클릭시
// const out = ref(null);
// onClickOutside(out, (event) => {
//   console.log(event);
//   drawer.value = false;
// });

// const dropdownHandler = (event) => {
//   console.log(event)
//   dropdown.value = false
// }

const houseInfo = ref([]);

const sido = ref([]);
const gugun = ref([]);
const dong = ref([]);
const sidoItem = ref();
const gugunItem = ref();
const dongItem = ref();

const drawer = ref(false);

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
  init();

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
  houseInfo.value = [];
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

  if (dong !== '선택없음') {
    getDongCodeByDongName(
      sidoItem.value,
      gugunItem.value,
      dongItem.value,
      ({ data }) => {
        getHouseInfoByDongCode(data.data, ({ data }) => {
          houseInfo.value = data.data;
        }),
          (error) => {
            console.log(error);
          };
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

const drawerReverse = () => {
  drawer.value = !drawer.value;
};

const detailView = (props) => {
  detailHouseInfo.value = props;
  drawer.value = false;
  location.value = {
    lat: 0,
    lng: 0,
  };
  location.value.lat = props.lat;
  location.value.lng = props.lng;

  getHouseDealsByAptCodeYearMonth(
    props.aptCode,
    ({ data }) => {
      detailDealInfo.value = data.data[0];
    },
    (error) => {
      console.log(error);
    }
  );
  drawer.value = true;
};

const markerDetail = (aptCode) => {
  const target = houseInfo.value.filter((item) => {
    return item.aptCode == aptCode;
  });
  detailHouseInfo.value = target[0];

  location.value = {
    lat: 0,
    lng: 0,
  };
  location.value.lat = target[0].lat;
  location.value.lng = target[0].lng;

  getHouseDealsByAptCodeYearMonth(
    aptCode,
    ({ data }) => {
      detailDealInfo.value = data.data[0];
    },
    (error) => {
      console.log(error);
    }
  );
  drawer.value = true;
};

const detailHouseInfo = ref({});
const detailDealInfo = ref({});
const location = ref({
  lat: 0,
  lng: 0,
});

onClickOutside;

const init = () => {
  gugun.value = [];
  dong.value = [];
  houseInfo.value = [];
};
</script>

<template>
  <div class="flex h-full overflow-y-hidden">
    <div class="relative w-1/4 overflow-y-auto h-[calc(100vh-69px)] scrollbar-hide z-30" ref="out">
      <div
        v-show="houseInfo == null || houseInfo.length == 0"
        class="flex flex-col items-center justify-center h-full pb-20"
      >
        <img
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Animals/Bird.png"
          alt="Bird"
          width="75"
          height="75"
        />
        <div class="font-semibold pt-4 text-center">
          <p>검색 결과가 없습니다</p>
          <p>새로운 집을 찾으러가볼까요?</p>
        </div>
      </div>
      <ApartmentListItem @detail-item="detailView" v-for="(item, index) in houseInfo" :key="index" :item="item" />
    </div>
    <div class="w-3/4 h-full relative">
      <!-- 검색 창 부분 -->
      <div class="absolute top-0 w-full pl-4 flex h-16 items-center z-10">
        <div class="flex gap-4">
          <VSelectMenu @item-click="sidoItemClick" class="w-48 shadow-lg" :arr="sido" />
          <VSelectMenu @item-click="gugunItemClick" class="w-48 shadow-lg" :arr="gugun" />
          <VSelectMenu @item-click="dongItemClick" class="w-48 shadow-lg" :arr="dong" />
        </div>
        <div class="pl-10 flex gap-4 items-center">
          <input
            type="text"
            placeholder="검색어를 입력하세요"
            class="h-9 w-72 text-sm border-0 border-black/5 text-gray-700 ring-1 shadow-lg ring-gray-400 placeholder:text-gray-400 focus:ring-1 focus:ring-slate-700"
          />
          <div
            class="h-9 py-[1.15rem] shadow-lg bg-teal-600 text-white text-sm font-semibold w-16 flex justify-center items-center border border-gray-400"
          >
            검색
          </div>
        </div>
      </div>
      <!-- 카카오 맵 부분 -->
      <KakaoMap @marker-click="markerDetail" :houseInfo="houseInfo" />
      <!-- 드로워 부분 -->
      <div
        v-show="location.lat != 0 && location.lng != 0"
        :class="drawer ? 'open' : 'close'"
        id="drawer"
        class="absolute top-0 z-20 bg-white w-2/5 h-full border border-t-0 overflow-auto scrollbar-hide"
      >
        <!-- @click="drawerReverse"  -->
        <div class="flex justify-end">
          <button type="button" class="mr-4 mt-2 rounded-sm text-gray-400" @click="drawerReverse">
            <XMarkIcon class="h-6 w-6" aria-hidden="true" />
          </button>
        </div>
        <!-- 로드뷰 -->
        <RoadView v-if="location" :location="location" />
        <div class="p-4 pt-0 flex flex-col gap-1 border-b">
          <div class="flex justify-between">
            <p class="font-bold text-lg">{{ detailHouseInfo.apartmentName }}</p>
            <p class="border-2 bg-teal-600 border-none text-white rounded-sm font-bold p-1 text-base">
              {{ detailDealInfo.dealAmount }}만원
            </p>
          </div>
          <p>
            {{ sidoItem }} {{ gugunItem }} {{ dongItem }} {{ detailHouseInfo.roadName }}
            {{ Number(detailHouseInfo.roadNameBonbun) == 0 ? '' : Number(detailHouseInfo.roadNameBonbun) }}
            {{ Number(detailHouseInfo.roadNameBubun) == 0 ? '' : Number(detailHouseInfo.roadNameBubun) }}
          </p>
          <p>최근 거래 {{ detailDealInfo.dealYear }}년 {{ detailDealInfo.dealMonth }}월</p>
          <div class="flex gap-2">
            <p class="text-teal-700 font-bold">면적 {{ detailDealInfo.area }}m<sup>2</sup></p>
            <p class="text-teal-700 font-bold">/ {{ detailDealInfo.floor }}층</p>
          </div>
        </div>
        <!-- 차트 -->
        <Highcharts class="border m-4 p-4" :options="options"></Highcharts>
        <!-- 뉴스 -->
        <NewsComponent />
      </div>
      <!-- </div> -->
    </div>
  </div>
</template>

<style scoped>
.open {
  animation: slideIn 0.6s forwards;
}
.close {
  animation: slideOut 0.6s forwards;
}

@keyframes slideIn {
  0% {
    opacity: 0;
    transform: translateX(-100%);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
}
@keyframes slideOut {
  0% {
    opacity: 1;
    transform: translateX(0);
  }
  100% {
    opacity: 0;
    transform: translateX(-100%);
  }
}
</style>
