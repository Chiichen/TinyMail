import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import { createRouter, createWebHistory } from 'vue-router';

/* 添加icon图标 */
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

/* 路由配置 */
import Home from "./components/Home.vue";
import Login from "./components/Login.vue";
import Writing from "./components/Writing.vue";
import Receiving from "./components/Receiving.vue";
import Set from "./components/Set.vue";
import Modify from "./components/Modify.vue";

import './assets/global.css'
import App from './App.vue'
import 'element-plus/dist/index.css'
import email from "./components/email.vue";
import {mobileTipjs} from './js/tip.js'

const routes=[

    {
        path:'/Login',
        component: Login,
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
            ,
            {
                path:'/email/:id',
                component:email,
            }
            ,
            {
                path:'Modify',
                component:Modify,
            }
        ]
    },
    {
        path:'/set',
        component:Set,
    },
    


];

const router=createRouter({
    history:createWebHistory(),
    routes,
})

const app=createApp(App);

app.use(ElementPlus)
app.use(router).mount('#app');

app.config.globalProperties.$tips = mobileTipjs;

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
