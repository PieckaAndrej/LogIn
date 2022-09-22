import {Box, Typography, Icon, useTheme} from "@mui/material";

const Navbar = () => {
	const theme = useTheme();

	return (
		<Box className="navbar">
			<Icon className="navbar-icon material-symbols-outlined" sx={{ color: theme.palette.primary.main, fontSize: "2.5rem"}}>
				cloud
			</Icon>
			<Typography variant="h4" sx={{ fontFamily: "'Quicksand', sans-serif" }}>
				Cloud
			</Typography>
		</Box>
	)
}

export default Navbar;
