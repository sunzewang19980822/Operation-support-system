import {
	createRouter,
	createWebHistory
} from 'vue-router'
import {
	ElMessage
} from 'element-plus'

const router = createRouter({
	history: createWebHistory(
		import.meta.env.BASE_URL),
	routes: [{
			path: '/',
			redirect:"/login"
		},
		{
			path: '/about',
			name: 'about',
			// route level code-splitting
			// this generates a separate chunk (About.[hash].js) for this route
			// which is lazy-loaded when the route is visited.
			component: () => import('../views/AboutView.vue')
		},
		{
			path: '/login',
			name: 'login',
			component: () => import('@/views/Login.vue')
		},
		{
			path: '/main',
			name: 'main',
			component: () => import('@/views/Main.vue'),
			children:[
				{
					path: 'user',
					component: () => import('@/views/User.vue'),
				}
			]
		},
	]
})

// vueRoute导航守卫（拦截器或过滤器）
router.beforeEach((to, from, next) => {
	// 如果用户访问的登录页，直接放行
	if (to.path === '/login' || to.path === '/')
		return next();
	const tokenStr = window.sessionStorage.getItem('token');
	// 没有token 强制跳转到登录页
	if (!tokenStr) {
		// 您还没有登录,请首先登录
		ElMessage('您还没有登录,请首先登录')
		return next('/login')
	}
	next(); //其它一律放行
});

export default router
