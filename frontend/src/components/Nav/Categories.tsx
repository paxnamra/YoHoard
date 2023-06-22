'use client'
import React from 'react'
import { Box, InputAdornment, TextField, Stack, Typography } from '../../config/mui'
import SearchIcon from '@mui/icons-material/Search'

export const Categories = () => {
	return (
		<Box
			flexDirection={'column'}
			width={'100%'}
			sx={{
				borderRight: '1px solid grey',
				borderLeft: '1px solid grey',
			}}>
			<TextField
				color='secondary'
				fullWidth
				placeholder='Looking for...'
				id='browser'
				aria-label='browser'
				variant='standard'
				InputProps={{
					startAdornment: (
						<InputAdornment
							position='start'
							sx={{ marginLeft: '10px' }}>
							<SearchIcon />
						</InputAdornment>
					),
				}}
				sx={{
					'& input': {
						paddingBlock: '10px',
					},
				}}
			/>
			<Stack direction={'column'} padding={2}>
				<Typography>Accordion here</Typography>
				<Typography>Accordion here</Typography>
				<Typography>Accordion here</Typography>
			</Stack>
		</Box>
	)
}
