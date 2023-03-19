import http from "./http.js";
export default{
	getRoleList(queryInfo){
		return http({
		  url: `/role`,
		  method: "get",
		  // params: queryInfo,
		});
	},
	addRole(addForm){
		return http({
		  url: `/role`,
		  method: "post",
		  data: addForm,
		});
	}
	
}
