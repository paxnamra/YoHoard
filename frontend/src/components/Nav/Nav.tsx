'use client'
import React from 'react'
import InsertDriveFileIcon from '@mui/icons-material/InsertDriveFile'
import FolderIcon from '@mui/icons-material/Folder'
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward'
import MoreHorizIcon from '@mui/icons-material/MoreHoriz'
import { Box } from '../../config/mui'
import { Button } from './Button'

const iconButtons = [
	{ aria: 'file', icon: <InsertDriveFileIcon /> },
	{ aria: 'folder', icon: <FolderIcon /> },
	{ aria: 'arrow', icon: <ArrowDownwardIcon /> },
	{ aria: 'ellipsis', icon: <MoreHorizIcon /> },
]

export const Nav = () => {
	return (
		<Box
			component='nav'
			display={'flex'}
			flexDirection={'column'}
			alignItems={'center'}
			gap={1.5}
			mt={5}
			width={'50px'}>
			{iconButtons.map((item, index) => (
				<Button
					key={index}
					aria={item.aria}>
					{item.icon}
				</Button>
			))}
		</Box>
	)
}
