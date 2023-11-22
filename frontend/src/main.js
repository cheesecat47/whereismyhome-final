import './assets/main.css';

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import './index.css';
import App from './App.vue';
import router from './router';

// icon
import '@fortawesome/fontawesome-free/js/all.js';

// chart를 위한 highcharts 라이브러리
import Highcharts from 'highcharts';
import VueHighcharts from 'vue-highcharts';

// 외부 영역 클릭시 동작하기 위한 라이브러리
import vClickOutside from 'v-click-outside';

const app = createApp(App);

app.use(vClickOutside);
app.use(VueHighcharts, { Highcharts });
app.use(createPinia());
app.use(router);

app.mount('#app');
