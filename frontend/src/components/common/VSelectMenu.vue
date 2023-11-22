<script setup>
import { ref, watch } from 'vue';
import { Listbox, ListboxButton, ListboxOptions, ListboxOption } from '@headlessui/vue';
import { CheckIcon, ChevronUpDownIcon } from '@heroicons/vue/20/solid';

const props = defineProps({ arr: Array });
const selectItem = ref('선택없음');

const emit = defineEmits(['itemClick']);

watch(selectItem, (newValue) => {
  emit('itemClick', newValue);
});

watch(props, (newValue) => {
  if (newValue.arr !== null && newValue.arr.length == 0) {
    selectItem.value = '선택없음';
  }
});
</script>

<template>
  <div class="w-28">
    <Listbox v-model="selectItem">
      <div class="relative">
        <ListboxButton
          class="relative w-full cursor-default bg-white py-2 pl-3 pr-5 text-left border border-black/30 text-sm"
        >
          <span class="block truncate">{{ selectItem }}</span>
          <span class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
            <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true" />
          </span>
        </ListboxButton>

        <transition>
          <ListboxOptions
            class="absolute mt-1 max-h-60 w-full overflow-auto scrollbar-thin scrollbar-track-gray-200 scrollbar-thumb-gray-400 bg-white py-1 text-base shadow-lg ring-1 ring-black/5 focus:outline-none sm:text-sm z-20"
          >
            <ListboxOption v-slot="{ active, selected }" v-for="(value, index) in arr" :key="index" :value="value">
              <li
                class="relative cursor-default select-none py-2 pl-10 pr-4 z-20"
                :class="{
                  'bg-slate-100 text-gray-900': active,
                  'text-gray-900 bg-white': !active,
                }"
              >
                <span>{{ value }}</span>
                <span v-if="selected" class="absolute inset-y-0 left-0 flex items-center pl-3">
                  <CheckIcon class="h-5 w-5 text-black" aria-hidden="true" />
                </span>
              </li>
            </ListboxOption>
          </ListboxOptions>
        </transition>
      </div>
    </Listbox>
  </div>
</template>
