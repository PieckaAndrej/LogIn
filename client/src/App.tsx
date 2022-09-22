import {CssBaseline} from '@mui/material';
import {ThemeProvider} from '@mui/material/styles';
import './App.scss';
import Login from './components/Login';
import Navbar from './components/Navbar';
import Register from './components/Register';
import {lightTheme} from './customTheme';

const App = () => {

	const firstLogin: boolean = localStorage.getItem("firstLogin") === null;

	return (
		<div className="App">
			<ThemeProvider theme={lightTheme}>
				<CssBaseline enableColorScheme/>
				<Navbar />
				{firstLogin ? <Register /> : <Login />}
			</ThemeProvider>
		</div>
	);
}

export default App;
