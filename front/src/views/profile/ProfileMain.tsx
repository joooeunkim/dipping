import { Box, Image } from '@chakra-ui/react';
import { ModalNavBar } from '../../components/ModalNavBar';

export const ProfileMain = () => {
  const props = {
    title: '프로필',
    leftElement: (
      <Image src="/logo_icon.png" alt="logo" objectFit="contain" h="32px" margin="2px" />
    ),
    rightElement: <Box className="fa-light fa-bars" lineHeight="36px" fontSize="24px" bg="" />,
  };

  return (
    <Box>
      <ModalNavBar {...props} />
      ProfileMain
    </Box>
  );
};
