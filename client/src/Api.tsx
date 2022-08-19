import Account from "./interfaces/Account";

export const BACK_END_IP: string = "192.168.1.2:8080";

export const postAccount = async (account: Account) => {
	const axios = require('axios');

	const instance = axios.create({
		baseURL: "http://192.168.1.2:8080",
		timeout: 1000,
	});

	await instance({
		method: "post",
		url: "/account/add",
		data: account 
	});

}
