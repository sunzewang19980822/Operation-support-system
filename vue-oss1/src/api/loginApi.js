import http from "./http.js";
export default{
	login(loginForm){
		return http({
		  url: `/login`,
		  method: "post",
		  data: loginForm,
		});
	}
}
