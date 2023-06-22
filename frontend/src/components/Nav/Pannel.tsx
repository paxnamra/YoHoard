import React from 'react'
import { Nav } from './Nav'
import { Categories } from './Categories'
import { User } from './User'
import { Box, Stack } from '../../config/mui'

export const Pannel = () => {
	return (
		<header className='flex flex-col h-screen max-w-[325px] '>
			<Stack
				direction={'row'}
				height={'100%'}>
				<nav>
					<Nav />
				</nav>
				<Categories />
			</Stack>
			<User />
		</header>
	)
}
