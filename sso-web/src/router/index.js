import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory('/sso/web'),
    routes: [
        {
            path: '/',
            name: 'index',
            component: () => import('../components/Home.vue')
        },
        {
            path: '/home',
            name: 'home',
            component: () => import('../components/Home.vue')
        },
        {
          path: '/login',
          name: 'login',
          component: () => import('../components/login/Login.vue')
        }
    ]
})

const whiteList = ["/login"]

router.beforeEach(async (to, from, next) => {
    // set page title
    var token = localStorage.getItem('token');
    if (token) {
        next();
    } else if (whiteList.some((m) => to.path.startsWith(m))) {
        next();
    } else {
        next({path: `/login`})
    }
});

export default router