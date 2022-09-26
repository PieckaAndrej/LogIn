import {Button,
	Card,
	CardContent,
	Checkbox,
	FormControl,
	FormHelperText,
	Grid,
	Icon,
	IconButton,
	Input,
	InputAdornment,
	InputLabel,
	TextField,
	Typography,
	useTheme
} from "@mui/material";
import {Box} from "@mui/system";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import RegisterInfo from "../interfaces/RegisterInfo";
import { hashPassword } from "../security";
import { postAccount } from "../Api";


const Register = () => {

	const theme = useTheme();

	const [showPassword, setShowPassword] = useState({
		password: false,
		repeatPassword: false
	});

	const [info, setInfo] = useState<RegisterInfo>({
		firstName: "",
		lastName: "",
		username: "",
		email: "",
		password: "",
		repeatPassword: "",
		termsAccepted: false,
	});

	const [error, setError] = useState({
		emailTaken: false,
		usernameTaken: false,
		invalidPassword: false,
		differentPassword: false,
		terms: false
	});

	const handleClickShowPassword = (name: string, value: boolean) => {
		setShowPassword(prevShowPassword => {
			return {
				...prevShowPassword,
				[name]: value
			}
		});
	};

	const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
		setInfo(prevInfo => {
			return {
				...prevInfo,
				[event.target.name]: event.target.value
			}
		})
	}

	const handleError = (name: string, value: boolean) => {
		setError(prevError => {
			return {
				...prevError,
				[name]: value
			}
		})
	} 

	// Check if email is not used already
	useEffect(() => {

	}, [info.email])

	// Check if username is not used already
	useEffect(() => {

	}, [info.username])

	const handleRegister = async (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		let errorHappened: boolean = false;

		for (let element in error) {
			handleError(element, false);
		}

		if (info.password.length < 8) {
			errorHappened = true;
			handleError("invalidPassword", true);
		}

		if (info.password !== info.repeatPassword) {
			errorHappened = true;
			handleError("differentPassword", true);
		}

		if (!info.termsAccepted) {
			errorHappened = true;
			handleError("terms", true);
		}

		if (!errorHappened) {
			const salt = new Uint8Array(16);
			window.crypto.getRandomValues(salt);

			const passwordHash = await hashPassword(info.password, salt)

			postAccount({
				firstName: info.firstName,
				lastName: info.lastName,
				username: info.username,
				email: info.email,
				passwordHash: passwordHash,
				clientSalt: salt.toString(),
			})
		}
	}

	console.log("render");

	return (
		<Box className="register">
			<Typography variant="h4" sx={{ color: theme.palette.primary.main, fontFamily: "'Quicksand', sans-serif" }}>
				Register
			</Typography>
			<Card sx={{ overflowY: "auto" }} className="register-card">
				<CardContent>
					<form onSubmit={handleRegister}>
						<Box className="register-card-container">
							<Grid container spacing={2}>
								<Grid item xs={6}>
									<TextField 
										id="givenName" 
										label="First name" 
										variant="standard"
										name="firstName"
										value={info.firstName}
										onChange={(event) => handleChange(event)}
										sx={{ minWidth: "100%" }}
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
										sx={{ minWidth: "100%" }}
									/>
								</Grid>
								<Grid item xs={12}>
									<TextField 
										error={error.usernameTaken}
										id="username" 
										label="Username" 
										variant="standard"
										name="username"
										value={info.username}
										onChange={(event) => handleChange(event)}
										sx={{ minWidth: "100%" }}
									/>
								</Grid>
								<Grid item xs={12}>
									<TextField 
										error={error.emailTaken}
										id="email" 
										type="email"
										label="Email" 
										variant="standard"
										name="email"
										value={info.email}
										onChange={(event) => handleChange(event)}
										sx={{ minWidth: "100%" }}
									/>
								</Grid>
								<Grid item xs={6}>
									<FormControl variant="standard" sx={{ minWidth: "100%" }}>
										<InputLabel htmlFor="password" error={error.invalidPassword}>Password</InputLabel>
										<Input
											id="password"
											name="password"
											error={error.invalidPassword}
											type={showPassword.password ? "text" : "password"} 
											value={info.password}
											onChange={(event) => handleChange(event)}
											endAdornment={
												<InputAdornment position="end">
													<IconButton
														onClick={() => handleClickShowPassword("password", !showPassword.password)}
														edge="end"
													>
														{showPassword.password ? 
															<Icon className="register-password-icon material-symbols-outlined">
																visibility
															</Icon>
															: 
															<Icon className="register-password-icon material-symbols-outlined">
																visibility_off
															</Icon>
														}
													</IconButton>
												</InputAdornment>
											}
										/>
										<FormHelperText 
											error={error.invalidPassword}
										>Must be at least 8 characters</FormHelperText>
									</FormControl>
								</Grid>
								<Grid item xs={6}>
									<FormControl variant="standard"sx={{ minWidth: "100%" }}>
										<InputLabel htmlFor="passwordRepeat" error={error.differentPassword}>Repeat password</InputLabel>
										<Input
											id="passwordRepeat"
											name="repeatPassword"
											error={error.differentPassword}
											type={showPassword.repeatPassword ? "text" : "password"} 
											value={info.repeatPassword}
											onChange={(event) => handleChange(event)}
											sx={{ minWidth: "100%" }}
											endAdornment={
												<InputAdornment position="end">
													<IconButton
														onClick={() => handleClickShowPassword("repeatPassword", !showPassword.repeatPassword)}
														edge="end"
													>
														{showPassword.repeatPassword ? 
															<Icon className="register-password-icon material-symbols-outlined">
																visibility
															</Icon>
															: 
															<Icon className="register-password-icon material-symbols-outlined">
																visibility_off
															</Icon>
														}
													</IconButton>
												</InputAdornment>
											}
										/>
										{error.differentPassword && <FormHelperText 
											error
										>The password doesn't match</FormHelperText>}
									</FormControl>
								</Grid>
							</Grid>
							<Box 
								sx={{ display: "flex",
									alignItems: "center",
								color: ( error.terms ? theme.palette.error.main : theme.palette.grey[500] ),
								marginRight: "auto"
								}}
							>
								<Checkbox 
									size="small" 
									name="termsAccepted" 
									value={info.termsAccepted} 
									onChange={(event) => {
										handleChange(event);
										handleError("terms", false);
									}}
									sx={ error.terms ? {color: theme.palette.error.main} : {} }
								/>
								<Typography>
									I aggree with&nbsp;
									<Link to="/login" className="link">terms and conditions</Link>
								</Typography>
							</Box>
							<Button variant="contained" type="submit" color="secondary">Register</Button>
							<Box sx={{ display: "flex", alignItems: "center", gap: "5px", color: theme.palette.grey[500] }}>
								<Typography sx={{ fontSize: "0.9rem" }}>
									Already have an account?
								</Typography>
								<Typography sx={{ fontSize: "0.9rem" }}>
									<Link to="/login" className="link">Log in</Link>
								</Typography>
							</Box>
						</Box>
					</form>
				</CardContent>
			</Card>
		</Box>
	)
}

export default Register;
