import {CssBaseline} from '@mui/material';
import {ThemeProvider} from '@mui/material/styles';
import {useEffect} from 'react';
import {Outlet, useNavigate} from "react-router-dom";
import './App.scss';
import Navbar from './components/Navbar';
import {lightTheme} from './customTheme';

const App = () => {

	const firstLogin: boolean = localStorage.getItem("firstLogin") === null;
	const navigate = useNavigate();

	useEffect(() => {
		navigate(firstLogin ? "/register" : "/login");
	}, [])

	return (
		<div className="App">
			<ThemeProvider theme={lightTheme}>
				<CssBaseline enableColorScheme/>
				<Navbar />
				<Outlet />
			</ThemeProvider>
		</div>
	);
}

export default App;
