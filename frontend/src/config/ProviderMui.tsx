'use client'
import React from 'react'
import { ThemeProvider } from './mui'
import { CacheProvider } from '@emotion/react'
import theme from './theme'
import createEmotionCache from './createEmotionCache'

const clientSideEmotionCache = createEmotionCache()

export const ProviderMui = ({ children }: { children: React.ReactNode }) => {
	return (
		<CacheProvider value={clientSideEmotionCache}>
			<ThemeProvider theme={theme}>{children}</ThemeProvider>
		</CacheProvider>
	)
}
