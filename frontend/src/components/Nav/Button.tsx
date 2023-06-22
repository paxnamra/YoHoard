import React from 'react'
import { IconButton } from '../../config/mui'

type ButtonProps = {
	aria: string
	children: React.ReactNode
}

export const Button = ({ aria, children }: ButtonProps) => {
	return (
		<IconButton
			aria-label={aria}
			sx={{
				'&:hover': {
					color: 'primary.light',
					transition: 'color .3s, background-color .3s',
				},
				width: '35px',
				height: '35px',
			}}>
			{children}
		</IconButton>
	)
}
