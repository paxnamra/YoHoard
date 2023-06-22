import { createTheme } from './mui'
import { grey, red } from '@mui/material/colors'

// Create a theme instance.
const theme = createTheme({
	palette: {
		primary: {
			main: '#556cd6',
			light: '#56f621',
		},
		secondary: {
			main: '#56f621',
            "100": '#b4afaf'
		},
        common: {
            black: '#556cd6',
        },
		error: {
			main: red.A400,
		},
	},
})
export default theme
