<script setup>
import { ref, watch, onMounted } from 'vue';

var map;
const positions = ref([]);
const markers = ref([]);

const props = defineProps({ houseInfo: Array });
const emit = defineEmits(['markerClick']);

// watch(
//   () => props.houseInfo.value,
//   () => {
//     // 이동할 위도 경도 위치를 생성합니다
//     var moveLatLon = new kakao.maps.LatLng(props.selectStation.lat, props.selectStation.lng);

//     // 지도 중심을 부드럽게 이동시킵니다
//     // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
//     map.panTo(moveLatLon);
//   },
//   { deep: true }
// );

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement('script');
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY}`;
    script.onload = () =>
      kakao.maps.load(() => {
        initMap();
      });
    document.head.appendChild(script);
  }
});

watch(
  () => props.houseInfo,
  (newValue) => {
    if (newValue !== undefined && newValue !== null && newValue[0] != null) {
      positions.value = [];

      props.houseInfo.forEach((station) => {
        let obj = {};
        obj.latlng = new kakao.maps.LatLng(station.lat, station.lng);
        obj.aptCode = station.aptCode;

        positions.value.push(obj);
      });
      loadMarkers();
    } else {
      deleteMarkers();
    }
  },
  { deep: true }
);

const initMap = () => {
  const container = document.getElementById('map');
  const options = {
    center: new kakao.maps.LatLng(36.106851, 128.417507),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
  // var mapTypeControl = new kakao.maps.MapTypeControl();
  // map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

  // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
  // var zoomControl = new kakao.maps.ZoomControl();
  // map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
  // loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    // console.log(position);
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      // title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });

    window.kakao.maps.event.addListener(marker, 'click', function () {
      emit('markerClick', position.aptCode);

      // 이동할 위도 경도 위치를 생성합니다
      // var moveLatLon = new kakao.maps.LatLng(props.selectStation.lat, props.selectStation.lng);

      // 지도 중심을 부드럽게 이동시킵니다
      // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
      map.panTo(position.latlng);
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div id="map" class="w-full h-[calc(100vh-69px)]"></div>
</template>

<style></style>
