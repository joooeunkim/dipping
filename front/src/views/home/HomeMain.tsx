import { Box } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom';
import { MainNavBar } from '../../components/MainNavBar';

export const HomeMain = () => {
  return (
    <Box>
      <MainNavBar />
      HomeMain
    </Box>
  );
};
