import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import './assets/main.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)
// app.use(ElementPlus, { size: 'small', zIndex: 3000 })
app.use(ElementPlus)
app.provide("appCation","云创动力运营支撑系统");

app.mount('#app')
