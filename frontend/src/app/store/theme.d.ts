import { ThemeOptions, Theme } from '../../lib/mui'

declare module '@mui/material/styles' {
	interface Theme {
		status: {
			white: string
		}
	}
	interface ThemeOptions {
		status: {
			white: React.CSSProperties['color']
		}
	}
}
