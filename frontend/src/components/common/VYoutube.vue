<template>
  <YouTube src="https://www.youtube.com/watch?v=1aJxwyRKJL0" @ready="onReady" ref="youtube" />
</template>

<script>
import { defineComponent } from 'vue';
import YouTube from 'vue3-youtube';

export default defineComponent({
  components: { YouTube },
  methods: {
    onReady() {
      this.$refs.youtube.playVideo();
    },
  },
});
</script>
