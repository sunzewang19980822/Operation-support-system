<script setup>
	import {
		ref,
		reactive,
		getCurrentInstance,
		inject
	} from "vue";
	import {
		useRouter
	} from 'vue-router'
	import * as Icons from '@element-plus/icons-vue'
	import {
		ElMessage
	} from 'element-plus'
	// import '@/mock/mockLogin.js'
	// import loginApi from '@/api/loginApi.js'
	const appCation = inject("appCation");
	const userRouter = useRouter();
	//折叠功能
	const {
		isCollapse,
		toggleCollapse
	} = useCollapse();
	const iconsObj = {
		'Document': Icons.Document,
		'4': Icons.Menu,
		'7': Icons.Tools,
		'9': Icons.TrendCharts,
		'Avatar': Icons.Avatar,
	}

	function useCollapse() {
		// 是否折叠
		const isCollapse = ref(false);
		// 点击按钮，切换菜单的折叠与展开
		function toggleCollapse() {
			isCollapse.value = !isCollapse.value;
		}
		return {
			isCollapse,
			toggleCollapse
		}
	}
	// 被激活的链接地址
	const activePath = ref('');

	activePath.value = window.sessionStorage.getItem('activePath');

	// 保存链接的激活状态
	function saveNavState(_activePath) {
		window.sessionStorage.setItem('activePath', _activePath)
		activePath.value = _activePath
	}

	function logout() {
		window.sessionStorage.clear();
		userRouter.push('/login');
	};

	const menulist = ref();
	menulist.value = JSON.parse(window.sessionStorage.getItem('currentUser')).promisses;
</script>

<template>
	<el-container class="home-container">
		<!-- 头部区域 -->
		<el-header>
			<div>
				<img src="../assets/logo.png" alt="{{appCation}}">
				<span>{{appCation}}</span>
			</div>
			<el-button type="info" @click="logout">退出</el-button>
		</el-header>
		<!-- 页面主体区域 -->
		<el-container>
			<!-- 侧边栏 -->
			<el-aside :width="isCollapse ? '64px' : '200px'">
				<div class="toggle-button" @click="toggleCollapse">|||</div>
				<!-- 侧边栏菜单区域 -->
				<el-menu background-color="#333744" text-color="#fff" active-text-color="#409EFF" unique-opened
					:collapse="isCollapse" :collapse-transition="false" router :default-active="activePath">
					<el-sub-menu :index="item.id + ''" v-for="item in menulist" :key="item.id">
						<!-- 一级菜单的模板区域 -->
						<template #title>
							<el-icon>
								<component :is="iconsObj[item.icon]"></component>
							</el-icon>
							<!-- 文本 -->
							<span>{{item.name}}</span>
						</template>
						<!-- 二级菜单 -->
						<el-menu-item v-for="subItem in item.children" :index="'/' + subItem.path" :key="subItem.id"
							@click="saveNavState('/' + subItem.path)">
							<el-icon>
								<Icons.Document />
							</el-icon>
							<span>{{subItem.name}}</span>
						</el-menu-item>

					</el-sub-menu>


				</el-menu>
			</el-aside>
			<!-- 右侧内容主体 -->
			<el-main>
				<!-- 路由占位符 -->
				<router-view></router-view>
			</el-main>
		</el-container>
	</el-container>
</template>

<style lang="less" scoped>
	.home-container {
		height: 100%;
	}

	.el-header {
		background-color: #373d41;
		display: flex;
		justify-content: space-between;
		padding-left: 0;
		align-items: center;
		color: #fff;
		font-size: 20px;

		>div {
			display: flex;
			align-items: center;

			span {
				margin-left: 15px;
			}
		}
	}

	.el-aside {
		background-color: #333744;

		.el-menu {
			border-right: none;
		}
	}

	.el-main {
		background-color: #eaedf1;
	}

	.iconfont {
		margin-right: 10px;
	}

	.toggle-button {
		background-color: #4a5064;
		font-size: 10px;
		line-height: 24px;
		color: #fff;
		text-align: center;
		letter-spacing: 0.2em;
		cursor: pointer;
	}
</style>
