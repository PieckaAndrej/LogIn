export default interface Account {
  firstName: string;
  lastName: string;
	username: string;
  email: string;
  passwordHash: string;
	clientSalt: string;
}
