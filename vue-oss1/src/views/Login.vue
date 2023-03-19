<script setup>
	// export setup(props, context) {
	// 	const a=ref(12);
	// 	return {
	// 		a
	// 	}
	// }
	import {
		ref,
		reactive,
		getCurrentInstance
	} from "vue";
	import {
		useRouter
	} from 'vue-router'
	import {
		User,
		Lock
	} from '@element-plus/icons-vue'
	import {
		ElMessage
	} from 'element-plus'
	import '@/mock/mockLogin.js'
	import loginApi from '@/api/loginApi.js'
	const userRouter = useRouter();
	
	const loginForm = reactive({
		loginName: '13100010001',
		password: '123456'
	})
	const loginFormRef = ref(null); //表单的组件对象
	// 这是表单的验证规则对象
	const loginFormRules = {
		// 验证用户名是否合法
		loginName: [{
				required: true,
				message: '请输入登录名称',
				trigger: 'blur'
			},
			{
				min: 2,
				max: 30,
				message: '长度在 2 到 30 个字符',
				trigger: 'blur'
			}
		],
		// 验证密码是否合法
		password: [{
				required: true,
				message: '请输入登录密码',
				trigger: 'blur'
			},
			{
				min: 6,
				max: 15,
				message: '长度在 6 到 15 个字符',
				trigger: 'blur'
			}
		]
	}
	// 重置表单
	function resetLoginForm() {
		loginFormRef.value.resetFields()
	}

	function submitLogin() {
		loginFormRef.value.validate(async valid => {
			if (!valid) return;
			// 到服务端登录
			const res = await loginApi.login(loginForm);
			if (res.code !== 200) return ElMessage('登录失败！')
			sessionStorage.setItem("currentUser",JSON.stringify(res.data));
			// console.log(res.data.token)
			sessionStorage.setItem("token",res.data.token);
			
			ElMessage({
				message: '登录成功',
				type: 'success',
			})
			userRouter.push('/main');
		});
	}
</script>

<template>
	<div class="login_container">
		<div class="login_box">
			<!--头像区域-->
			<div class="avatar_box">
				<img src="../assets/logo.png" alt />
			</div>
			<!--表单区域-->
			<el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
				<el-form-item prop="loginName">
					<el-input placeholder="用户名/手机号/邮箱地址" :prefix-icon="User" v-model="loginForm.loginName">
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input v-model="loginForm.password" :prefix-icon="Lock" show-password>
					</el-input>
				</el-form-item>
				<el-form-item class="btns">
					<el-button type="primary" @click="submitLogin">登录</el-button>
					<el-button type="info" @click="resetLoginForm">重置</el-button>
				</el-form-item>
			</el-form>
		</div>
	</div>

</template>

<style lang="less" scoped>
	.login_container {
		height: 100%;
		// height: 850px;
		background: #2b4b6b;
	}

	.login_box {
		width: 450px;
		height: 300px;
		background: #fff;
		border-radius: 3px;
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);

		.avatar_box {
			padding: 10px;
			width: 130px;
			height: 130;
			border: 1px solid #eee;
			border-radius: 50%;
			box-shadow: 0 0 10px #ddd;
			position: absolute;
			left: 50%;
			transform: translate(-50%, -50%);
			background: #fff;

			img {
				width: 100%;
				height: 100%;
				border-radius: 50%;
				background: #eee;
			}
		}
	}

	.login_form {
		position: absolute;
		bottom: 0;
		width: 100%;
		padding: 0 20px;
		box-sizing: border-box;
	}

	.btns {
		display: flex;
		justify-content: flex-end;
	}
</style>
