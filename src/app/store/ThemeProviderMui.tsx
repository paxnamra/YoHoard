import { createTheme, ThemeProvider, colors } from '@mui/material'

const ThemeProviderMui = ({ children }: { children: React.ReactNode }) => {
	const theme = createTheme({

		//  Custom colors provides from MUI
		palette: {
			secondary: {
				main: colors.red[300],
				light: '#0066ff'

			},
		},
		// extends MUI colors - defines in 'theme.d.ts'
		status: {
			white: '#ffff',
		},
	})
	return <ThemeProvider theme={theme}>{children}</ThemeProvider>
}
export default ThemeProviderMui
