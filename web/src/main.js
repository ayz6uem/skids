import Vue from 'vue'
import VueReource from 'vue-resource'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router/index.js'
import VueAMap from 'vue-amap'

Vue.use(ElementUI)
Vue.use(VueReource)

Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
    key: '160cab8ad6c50752175d76e61ef92c50',
    plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
    v: '1.4.4'
});

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
