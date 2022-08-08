import { Box, VisuallyHidden } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom';
import { ColorModeSwitcher } from '../ColorModeSwitcher';
import { BottomTabBar } from '../components/floatingbar/BottomTabBar';
import { MainNavBar } from '../components/floatingbar/MainNavBar';
import { ModalNavBar } from '../components/floatingbar/ModalNavBar';
import { SearchNavBar } from '../components/floatingbar/SearchNavBar';

export const Layout = () => (
  <div>
    <Box h="48px" w="full" />
    <Outlet />
    <Box h="48px" w="full" />

    {/* ==Floating Component== */}
    <VisuallyHidden>
      <div id="player" />
    </VisuallyHidden>
    <BottomTabBar />
  </div>
);
