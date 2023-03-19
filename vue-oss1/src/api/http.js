import axios from "axios";
// 提示消息组件
import {
	ElLoading,
	ElMessage
} from "element-plus";

let http = axios.create({
	baseURL: "http://localhost/oss/v1/",
	timeout: 10000,
});

let loadingInstance;

// 拦截器的添加
http.interceptors.request.use(
	(config) => {
		loadingInstance = ElLoading.service("加载中");
		let token = window.sessionStorage.getItem('token');
		if (token) {
			config.headers.Authorization = token;
		}
		return config;
	},
	(err) => {
		loadingInstance?.close();
		ElMessage.error("网络异常");
		return Promise.reject(err);
	}
);

//响应拦截器
http.interceptors.response.use(
	(res) => {
		if (res.data.code == 400) {
			console.log("remove token");
			// window.sessionStorage.removeItem('token')
			window.sessionStorage.clear();
		}
		loadingInstance?.close();
		return res.data;
	},
	(err) => {
		loadingInstance?.close();
		ElMessage.error("请求失败");
		return Promise.reject(err);
	}
);
export default http;
