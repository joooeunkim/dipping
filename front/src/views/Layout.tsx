import { Box, VisuallyHidden } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom';
import { ColorModeSwitcher } from '../ColorModeSwitcher';
import { BottomTabBar } from '../components/floatingbar/BottomTabBar';
import { MainNavBar } from '../components/floatingbar/MainNavBar';
import { ModalNavBar } from '../components/floatingbar/ModalNavBar';
import { SearchNavBar } from '../components/floatingbar/SearchNavBar';
import { IFramePlayer } from '../components/musicplayer/IFramePlayer';

export const Layout = () => (
  <div>
    {/* ==Floating Component== */}
    <IFramePlayer />
    <BottomTabBar />

    <Box h="48px" w="full" />
    <Outlet />
    <Box h="48px" w="full" />
  </div>
);
