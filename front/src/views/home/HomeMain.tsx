import { Box } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom';
import { MainNavBar } from '../../components/MainNavBar';
import { PlaylistPost } from '../../components/PlaylistPost';

export const HomeMain = () => {
  const posts = [<PlaylistPost />, <PlaylistPost />];

  return (
    <Box>
      <MainNavBar />
      {posts[0]}
      {posts[1]}
    </Box>
  );
};
