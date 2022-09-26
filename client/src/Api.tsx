import Account from "./interfaces/Account";
import axios from "axios";

export const instance = axios.create({
		baseURL: "http://192.168.87.185:8080",
		timeout: 1000,
})

export const postAccount = async (account: Account) => {

	await instance.post("/account/register", account);

}
