import { Box, Button, Card, CardContent, TextField, Typography, useTheme } from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";
import { postLogin } from "../Api";

const Login = () => {
	const [info, setInfo] = useState({
		name: "",
		password: ""
	})

	const theme = useTheme();

	const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
		setInfo(prevInfo => {
			return {
				...prevInfo,
				[event.target.name]: event.target.value
			}
		})
	}

	const handleLogin = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const getResponse = async () => {
			const response = await postLogin(info.name, info.password);

			console.log(response);

		};

		getResponse();
	}


	return (
		<Box className="form login">
			<Typography variant="h4" sx={{ color: theme.palette.primary.main, fontFamily: "'Quicksand', sans-serif" }}>
				Log in
			</Typography>
			<Card sx={{ overflowY: "auto" }} className="card">
				<CardContent>
					<form onSubmit={handleLogin}>
						<Box className="card-container">
							<TextField 
								label="Email / Username" 
								variant="standard"
								name="name"
								type="username"
								value={info.name}
								onChange={(event) => handleChange(event)}
								sx={{ minWidth: "100%" }}
							/>
							<TextField 
								id="existingPassword"
								label="Password" 
								variant="standard"
								name="password"
								type="password"
								value={info.password}
								onChange={(event) => handleChange(event)}
								sx={{ minWidth: "100%" }}
							/>
							<Button variant="contained" type="submit" color="secondary">Login</Button>
							<Box sx={{ display: "flex", alignItems: "center", gap: "5px", color: theme.palette.grey[500] }}>
								<Typography sx={{ fontSize: "0.9rem" }}>
									Don't have an account?
								</Typography>
								<Typography sx={{ fontSize: "0.9rem" }}>
									<Link to="/register" className="link">Register</Link>
								</Typography>
							</Box>
						</Box>
					</form>
				</CardContent>
			</Card>
		</Box>
	)
}

export default Login;
