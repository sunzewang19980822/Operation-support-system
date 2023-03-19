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
	import userApi from '@/api/userApi.js'
	import roleApi from '@/api/roleApi.js'
	const appCation = inject("appCation");
	const userRouter = useRouter();
	// 查询操作
	function useQuery() {
		const queryInfo = reactive({
			query: '',
			// 当前的页数
			pagenum: 1,
			// 当前每页显示多少条数据
			pagesize: 10
		});
		const userlist = ref();
		const total = ref();
		async function getUserList() {
			const res = await userApi.getUserList({
				params: queryInfo
			});
			// const res = await axios.get('admin', {
			// 	params: queryInfo
			// })
			if (res.code !== 200) return ElMessage('失败！' + res.message)
			console.log(res.data)
			userlist.value = res.data;
			// userlist.value = res.data.content;
			// total.value = res.data.totalElements;
		}

		function handleSizeChange(newSize) {
			// queryInfo.pagesize = newSize
			getUserList()
		}
		// 监听 页码值 改变的事件
		function handleCurrentChange(newPage) {
			// queryInfo.pagenum = newPage
			getUserList()
		}
		return {
			queryInfo,
			userlist,
			total,
			getUserList,
			handleSizeChange,
			handleCurrentChange
		}
	}
	// cb=callback 回调函数
	const checkMobile = (rule, value, cb) => {
		// 验证手机号的正则表达式
		const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
		if (regMobile.test(value)) {
			return cb()
		}
		cb(new Error('请输入合法的手机号'))
	}

	const checkMail = (rule, value, cb) => {
		const reg = /^[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}$/
		if (reg.test(value)) {
			return cb()
		}
		cb(new Error('请输入合法的邮箱地址'))
	}

	function useAdd() {
		const addForm = reactive({
			name: '',
			password: '',
			mob: '',
			mail: ''
		});
		const addFormRef = ref();
		// 控制添加用户对话框的显示与隐藏
		const addDialogVisible = ref(false);
		const addFormRules = {
			name: [{
					required: true,
					message: '请输入用户名',
					trigger: 'blur'
				},
				{
					min: 3,
					max: 10,
					message: '用户名的长度在3~10个字符之间',
					trigger: 'blur'
				}
			],
			password: [{
					required: true,
					message: '请输入密码',
					trigger: 'blur'
				},
				{
					min: 6,
					max: 15,
					message: '密码的长度在6~15个字符之间',
					trigger: 'blur'
				}
			],
			mob: [{
					required: true,
					message: '请输入手机号',
					trigger: 'blur'
				},
				{
					validator: checkMobile,
					trigger: 'blur'
				}
			],
			mail: [{
					required: true,
					message: '请输入邮箱地址',
					trigger: 'blur'
				},
				{
					validator: checkMail,
					trigger: 'blur'
				}
			]
		}
		// 监听添加用户对话框的关闭事件
		function addDialogClosed() {
			addFormRef.value.resetFields();
			addDialogVisible.value = false;
		}

		function addUser() {
			addFormRef.value.validate(async valid => {
				if (!valid) return
				// 可以发起添加用户的网络请求
				const res = await userApi.addUser(addForm);
				if (res.code !== 200) return ElMessage('失败！' + res.message)
				ElMessage('添加用户成功！')
				// 隐藏添加用户的对话框
				addDialogVisible.value = false
				// 重新获取用户列表数据
				getUserList()
			})
		}
		return {
			addForm,
			addFormRef,
			addDialogVisible,
			addFormRules,
			addDialogClosed,
			addUser
		}
	}
	// 设置角色
	function useSetRole() {
		let userId;
		const roles = ref(); //佳节所有的角色
		const setRoleFormRef = ref();
		// 控制添加用户对话框的显示与隐藏
		const setRoleDialogVisible = ref(false);
		const userRoleIds = ref([]);
		// 监听添加用户对话框的关闭事件
		function setRoleDialogClosed() {
			userRoleIds.value.length = 0;
			setRoleDialogVisible.value = false;
		}
		// 展示编辑用户的对话框
		async function showSetRoleDialog(id) {
			let res = await roleApi.getRoleList();
			userId = id;
			if (res.code !== 200) {
				return ElMessage.error('查询角色信息失败！')
			}
			roles.value = res.data;

			res = await userApi.getUserById(id)
			if (res.code !== 200) {
				return ElMessage.error('查询用户信息失败！')
			}
			// for (let role of res.data.roles) {
			// 	userRoleIds.value.push(role.id);
			// }
			userRoleIds.value = Array.from(res.data.roles, (role)=>role.id);
			console.log(userRoleIds.value)
			setRoleDialogVisible.value = true
		}
		async function saveAdminRoles() {
			
			let parms = {
				id: userId,
				roleIds: userRoleIds.value
			};
			console.log(parms);
			// let parms={
			// 	id:userId,
			// 		roles:[
			// 		{
			// 			"id":1
			// 		},
			// 		{
			// 			"id":2
			// 		}
			// 	]
			// };
			// console.log(JSON.stringify(parms))
			let res = await userApi.setRole(parms);
			if (res.code !== 200) {
				return ElMessage.error('修改用户角色信息失败！')
			}
			setRoleDialogVisible.value = false;
			// 重新获取用户列表数据
			getUserList()
		}

		return {
			roles,
			setRoleFormRef,
			setRoleDialogVisible,
			setRoleDialogClosed,
			showSetRoleDialog,
			userRoleIds,
			saveAdminRoles,
			userId
		}
	}

	const {
		queryInfo,
		userlist,
		total,
		getUserList,
		handleSizeChange,
		handleCurrentChange
	} = useQuery();


	const {
		addForm,
		addFormRef,
		addDialogVisible,
		addFormRules,
		addDialogClosed,
		addUser
	} = useAdd();
	const {
		roles,
		setRoleFormRef,
		setRoleDialogVisible,
		setRoleDialogClosed,
		showSetRoleDialog,
		userRoleIds,
		saveAdminRoles
	} = useSetRole();
	
	getUserList();	
</script>

<template>
	<!-- 面包屑导航区域 -->
	<el-breadcrumb separator-class="el-icon-arrow-right">
		<el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
		<el-breadcrumb-item>用户管理</el-breadcrumb-item>
		<el-breadcrumb-item>用户列表</el-breadcrumb-item>
	</el-breadcrumb>
	<!-- 卡片视图区域 -->
	<el-card>
		<!-- 搜索与添加区域 -->
		<el-row :gutter="20">
			<el-col :span="8">
				<el-input placeholder="请输入内容" v-model.trim="queryInfo.query" clearable @clear="getUserList">
					<template #append>
						<el-button :icon="Icons.Search" @click="queryInfo.pagenum=1;getUserList();" />
					</template>
				</el-input>
			</el-col>
			<el-col :span="4">
				<el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
			</el-col>
		</el-row>
		<!-- 用户列表区域 -->
		<el-table :data="userlist" border stripe>
			<el-table-column type="index"></el-table-column>
			<el-table-column label="姓名" prop="name"></el-table-column>
			<el-table-column label="电话" prop="mob"></el-table-column>
			<el-table-column label="邮箱" prop="mail"></el-table-column>
			<el-table-column label="角色" prop="roleName"></el-table-column>
			<el-table-column label="操作">
				<template #default="scope">
					<!-- 修改按钮 -->
					<el-button type="primary" :icon="Icons.Edit" @click="showEditDialog(scope.row.id)">
					</el-button>
					<!-- 删除按钮 -->
					<el-button type="danger" :icon="Icons.Delete" @click="removeUserById(scope.row.id)">
					</el-button>
					<!-- 分配角色按钮 -->
					<el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
						<el-button type="warning" :icon="Icons.Setting" @click="showSetRoleDialog(scope.row.id)">
						</el-button>
					</el-tooltip>
				</template>
			</el-table-column>
		</el-table>
	</el-card>
	<!-- 添加用户的对话框 -->
	<el-dialog title="添加用户" v-model="addDialogVisible" width="50%" :before-close="addDialogClosed">
		<!-- 内容主体区域 -->
		<el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
			<el-form-item label="用户名" prop="name">
				<el-input v-model="addForm.name"></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="password">
				<el-input type="password" v-model="addForm.password"></el-input>
			</el-form-item>
			<el-form-item label="手机" prop="mob">
				<el-input v-model="addForm.mob"></el-input>
			</el-form-item>
			<el-form-item label="邮箱" prop="mail">
				<el-input v-model="addForm.mail"></el-input>
			</el-form-item>
		</el-form>
		<!-- 底部区域 -->
		<span slot="footer" class="dialog-footer">
			<el-button @click="addDialogClosed">取 消</el-button>
			<el-button type="primary" @click="addUser">确 定</el-button>
		</span>
	</el-dialog>
	<!-- 分配角色的对话框 -->
	<el-dialog title="分配角色" v-model="setRoleDialogVisible" width="50%" :before-close="setRoleDialogClosed">
		<div>
			<el-checkbox-group ref="setRoleFormRef" v-model="userRoleIds">
				<el-checkbox :label="role.id" v-for="(role,index) in roles" :key="index">
					{{role.name}}
				</el-checkbox>
			</el-checkbox-group>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button @click="setRoleDialogClosed">取 消</el-button>
			<el-button type="primary" @click="saveAdminRoles()">确 定</el-button>
		</span>
	</el-dialog>

</template>

<style lang="less" scoped>
</style>
