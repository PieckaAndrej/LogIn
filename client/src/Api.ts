import Account from "./interfaces/Account";
import axios from "axios";

export const instance = axios.create({
		baseURL: "http://10.34.156.250:8080",
		timeout: 1000,
})

export const postAccount = async (account: Account) => {
	return await instance.post("/account/register", account);
}

export const isIdAvailable = async (id: string) => {
	return await instance.get(`/account/id/${id}`);
}

export const postLogin = async (id: string, password: string) => {
	return await instance.post(`/account/login`, {id: id, password: password})
}
