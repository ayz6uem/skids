import Vue from 'vue'
import VueReource from 'vue-resource'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router/index.js'
import VueAMap from 'vue-amap'

Vue.use(ElementUI)
Vue.use(VueReource)

Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
    key: 'ab604fd9d01ff6585069076c2011980c',
    plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
    v: '1.4.4'
});

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
