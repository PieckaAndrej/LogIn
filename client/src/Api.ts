import Account from "./interfaces/Account";
import axios from "axios";

export const instance = axios.create({
		baseURL: "http://192.168.87.125:8080",
		timeout: 1000,
})

export const postAccount = async (account: Account) => {
	return await instance.post("/account/register", account);
}

export const isEmailAvailable = async (email: string) => {
	return await instance.get(`/account/email/${email}`);
}

export const isUsernameAvailable = async (username: string) => {
	return await instance.get(`/account/username/${username}`);
}
