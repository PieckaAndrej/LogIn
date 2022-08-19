import React from 'react';
import {useState} from 'react';
import {postAccount} from './Api';
import './App.css';

function App() {

	const [username, setUsername] = useState("");
	const [email, setEmail] = useState("");

	const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		postAccount({email: email, username: username});
	}

	return (
		<div className="App">
			<form onSubmit={handleSubmit}>
				<div>
					<p>Name</p>
					<input 
						type="name" 
						value={username}
						onChange={value => setUsername(value.target.value)}
					/>
				</div>
				<div>
					<p>Email</p>
					<input 
						type="email" 
						value={email}
						onChange={value => setEmail(value.target.value)}
					/>
				</div>
				<input type="submit" value="Register"/>
			</form>
		</div>
	);
}

export default App;
