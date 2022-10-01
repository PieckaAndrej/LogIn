import {Box, Button, Card, CardContent, TextField, Typography, useTheme} from "@mui/material";
import {useState} from "react";
import { Link } from "react-router-dom";

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


	return (
		<Box className="form login">
			<Typography variant="h4" sx={{ color: theme.palette.primary.main, fontFamily: "'Quicksand', sans-serif" }}>
				Login
			</Typography>
			<Card sx={{ overflowY: "auto" }} className="card">
				<CardContent>
					<form>
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
						</Box>
						<Button variant="contained" type="submit" color="secondary">Login</Button>
						<Box sx={{ display: "flex", alignItems: "center", gap: "5px", color: theme.palette.grey[500] }}>
							<Typography sx={{ fontSize: "0.9rem" }}>
								Don't have an account?
							</Typography>
							<Typography sx={{ fontSize: "0.9rem" }}>
								<Link to="/register" className="link">Register</Link>
							</Typography>
						</Box>
					</form>
				</CardContent>
			</Card>
		</Box>
	)
}

export default Login;
