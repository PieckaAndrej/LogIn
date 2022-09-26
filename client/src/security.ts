import {argon2id} from "hash-wasm"

export const hashPassword = async (password: string, salt: Uint8Array) => {
	// Normalize the UTF-8
	password = password.normalize();

	const hash = await argon2id({
		password: password,
		salt,
		parallelism: 1,
    iterations: 256,
    memorySize: 512, // use 512KB memory
    hashLength: 128, // output size = 128 bytes
    outputType: 'encoded', // return standard encoded string containing parameters needed to verify the key
	})

	console.log(hash.length)

	console.log(hash)

	return hash;

}
