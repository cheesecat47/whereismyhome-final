<script setup>
const props = defineProps({ item: Object });

const emit = defineEmits(['detailItem']);

const itemClick = () => {
  console.log(props.item);
  emit('detailItem', props.item);
};
</script>

<template>
  <div @click="itemClick" class="p-4 border-b bg-white cursor-pointer hover:bg-slate-100">
    <div class="flex gap-2">
      <span
        class="inline-flex items-center rounded-md bg-gray-100 px-2 py-1 text-xs font-medium text-gray-700 ring-1 ring-inset ring-gray-500/10"
        >아파트</span
      >
      <p class="font-bold">{{ item.apartmentName }}</p>
    </div>
    <div class="pl-1 pt-1">
      <div class="text-sm flex gap-1 pt-1">
        <p>도로명 주소</p>
        <p>{{ item.roadName }}</p>
        <p>{{ Number(item.roadNameBonbun) == 0 ? '' : Number(item.roadNameBonbun) }}</p>
        <p>{{ Number(item.roadNameBubun) == 0 ? '' : Number(item.roadNameBubun) }}</p>
      </div>
      <div class="text-sm flex gap-1 pt-1">
        <p>지번 주소</p>
        <p>{{ item.dong }}</p>
        <p>{{ Number(item.bonbun) == 0 ? '' : Number(item.bonbun) }}</p>
        <p>{{ Number(item.bubun) == 0 ? '' : Number(item.bubun) }}</p>
      </div>
      <div class="text-sm flex gap-1 pt-1">
        <p>준공 년도</p>
        <p>{{ item.buildYear }}년</p>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
