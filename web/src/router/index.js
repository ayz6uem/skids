import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            component: resolve => require(['../components/page/Cars.vue'], resolve)
        },
        {
            path: '/car/:id',
            component: resolve => require(['../components/page/Car.vue'], resolve)
        },
        {
            path: '/car/direction/:id',
            component: resolve => require(['../components/page/Direction.vue'], resolve)
        }
    ]
})
