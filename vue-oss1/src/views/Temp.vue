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
</script>

<template>
</template>

<style lang="less" scoped>
</style>
