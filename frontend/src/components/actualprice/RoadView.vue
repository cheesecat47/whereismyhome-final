<script setup>
import { onMounted, watch } from 'vue';

const props = defineProps({ location: Object });

onMounted(() => {
  console.log(props.location);
  if (props.location.lat != 0 && props.location.lng != 0) {
    var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
    var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
    var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

    var position = new kakao.maps.LatLng(props.location.lat, props.location.lng);

    // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
    roadviewClient.getNearestPanoId(position, 50, function (panoId) {
      roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
    });
  }
});

watch(
  () => props.location,
  () => {
    initLoadView(props.location.lat, props.location.lng);
  },
  { deep: true }
);

const initLoadView = (lat, lng) => {
  const roadviewContainer = document.getElementById('roadview');

  var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
  var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

  var position = new kakao.maps.LatLng(lat, lng);

  // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
  roadviewClient.getNearestPanoId(position, 200, function (panoId) {
    roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
  });
};
</script>

<template>
  <div id="roadview" class="w-[100%-1px] h-64 m-4 p-4 border"></div>
</template>

<style scoped></style>
