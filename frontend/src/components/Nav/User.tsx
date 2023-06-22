'use client'
import React from 'react'
import { Stack, Typography, Avatar } from '../../config/mui'
import SettingsIcon from '@mui/icons-material/Settings'
import { Button } from './Button'

export const User = () => {
	return (
		<Stack
			direction={'row'}
			alignItems={'center'}
			justifyContent={'space-between'}
			paddingY={2}
			fontSize={'1em'}
			paddingX={2}
			sx={{
				borderRight: '1px solid grey',
				borderTop: '1px solid grey',
			}}>
			<Avatar>YH</Avatar>
			<Typography
				fontSize={'.8em'}
				color={'GrayText'}
				variant='body2'
				component={'p'}>
				yohoarduser@gmail.com
			</Typography>
			<Button aria='settings'>
				<SettingsIcon fontSize='medium' />
			</Button>
		</Stack>
	)
}
