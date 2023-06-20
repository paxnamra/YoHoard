'use client'
import './globals.css'

import { Inter } from 'next/font/google'
import ThemeProviderMui from './store/ThemeProviderMui'
const inter = Inter({ subsets: ['latin'] })



export default function RootLayout({ children }: { children: React.ReactNode }) {
	return (
		<html lang='en'>
			<head>
				<meta
					name='viewport'
					content='width=device-width, initial-scale=1.0'
				/>
				<meta
					name='description'
					content='An app to keep in one place notes, bookmarks, links, and subscriptions informations to make life easier.'
				/>
				<title>YoHoard</title>
			</head>
			<body className={inter.className}>
				<ThemeProviderMui>{children}</ThemeProviderMui>
			</body>
		</html>
	)
}
