import {Button, Card, CardContent, Grid, TextField, Typography, useTheme} from "@mui/material";
import {Box} from "@mui/system";
import {useEffect, useState} from "react";

const Register = () => {

	const theme = useTheme();

	const [info, setInfo] = useState({
		firstName: "",
		lastName: "",
		email: "",
		username: "",
		phoneCountryCode: "",
		phoneNumber: "",
		password: "",
		repeatPassword: "",
	});

	const handleChange = (event: any): void => {
		setInfo(prevInfo => {
			return {
				...prevInfo,
				[event.target.name]: event.target.value
			}
		})
	}

	// Check if email is not used already
	useEffect(() => {

	}, [info.email])

	const handleRegister = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
	}

	console.log("render");

	return (
		<Box className="register">
			<Typography variant="h4" sx={{ color: theme.palette.primary.main, fontFamily: "'Roboto', sans-serif" }}>
				Register
			</Typography>
			<Card sx={{ borderTop: `5px solid ${theme.palette.primary.main}` }}>
				<CardContent>
					<form onSubmit={handleRegister}>
						<Box className="register-card">
							<Grid container spacing={2}>
								<Grid item xs={6}>
									<TextField 
										id="givenName" 
										label="First name" 
										variant="standard"
										name="firstName"
										value={info.firstName}
										onChange={(event) => handleChange(event)}
									/>
								</Grid>
								<Grid item xs={6}>
									<TextField 
										id="familyName" 
										label="Last name" 
										variant="standard"
										name="lastName"
										value={info.lastName}
										onChange={(event) => handleChange(event)}
									/>
								</Grid>
								<Grid item xs={12}>
									<TextField 
										id="email" 
										label="Email" 
										variant="standard"
										name="email"
										value={info.email}
										onChange={(event) => handleChange(event)}
									/>
								</Grid>
								<Grid item xs={6}>
									<TextField 
										type="password" 
										label="Password" 
										variant="standard"
										name="password"
										value={info.password}
										onChange={(event) => handleChange(event)}
									/>
								</Grid>
								<Grid item xs={6}>
									<TextField 
										type="password" 
										label="Repeat password" 
										variant="standard"
										name="repeatPassword"
										value={info.repeatPassword}
										onChange={(event) => handleChange(event)}
									/>
								</Grid>
							</Grid>
							<Button variant="contained" type="submit" color="secondary">Register</Button>
						</Box>
					</form>
				</CardContent>
			</Card>
		</Box>
	)
}

export default Register;
