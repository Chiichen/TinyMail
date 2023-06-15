import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import { createRouter, createWebHistory } from 'vue-router';

/* 路由配置 */
import Home from "./components/Home.vue";
import Login from "./components/Login.vue";
import Writing from "./components/Writing.vue";
import Receiving from "./components/Receiving.vue";

import './assets/global.css'
import App from './App.vue'
import 'element-plus/dist/index.css'

const routes=[

    {
        path:'/Login',
        component: Login,
    },
    {
        path:'/Writing',
        component:Writing
    },
    {
        path:'/Receiving',
        component:Receiving
    },
    {
        path:'/home',
        component: Home,
        children:[

            {
                path:'Writing',
                component: Writing
            },
            {
                path:'Receiving',
                component: Receiving
            }
        ]
    },



];

const router=createRouter({
    history:createWebHistory(),
    routes,
})

const app=createApp(App);

app.use(ElementPlus)
app.use(router).mount('#app');
