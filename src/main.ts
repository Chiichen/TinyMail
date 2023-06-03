import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import { createRouter, createWebHistory } from 'vue-router';
import Home from "./components/Home.vue";
import Login from "./components/login.vue";
import Writing from "./components/Writing.vue";
import Receiving from "./components/Receiving.vue";
import './assets/global.css'
import App from './App.vue'
import 'element-plus/dist/index.css'


createApp(App).use(ElementPlus).mount('#app')

const routes=[
    {
        path:'/Home',
        component: Home,
    },
    {
        path:'/Login',
        component: Login,
    },
    {
        path:'/Writing',
        component: Writing,
    },
    {
        path:'/Receiving',
        component: Receiving,
    },
];

const router=createRouter({
    history:createWebHistory(),
    routes,
})

const app=createApp(App);
app.use(router);
app.mount('#app');
