import http from "./http.js";
export default {
	getUserList(queryInfo) {
		// return http({
		//   url: `/user`,
		//   method: "get",
		//   params: queryInfo,
		// });
		return http.get('/user', {
			params: queryInfo
		})
	},
	getUserById(id) {
		return http({
			url: `/user/` + id,
			method: "get",
		});
	},
	addUser(addForm) {
		return http({
			url: `/user`,
			method: "post",
		 data: addForm,
		});
	},
	setRole(addForm) {
		return http({
		 url: `/user/role`,
			method: "patch",
			data: addForm,
		});
	}

}
