// 引入mockjs
import Mock from "mockjs";

// 获取 mock.Random 对象
const Random = Mock.Random;
Mock.mock("/login", "post", (params) => {
	console.log(2222222)
	let newData = JSON.parse(params.body);
	let obj = {
		"code": 200,
		"message": "登录成功",
		"data": {
			"id": 6,
			"name": "杜牧",
			"password": "ec89dd46c6577885962ce357db64aa35",
			"mob": "13100010006",
			"mail": "123@kfm.com",
			"roles": []
		}
	}
	obj.data.id = Random.guid();
	return obj;

});
