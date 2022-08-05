import { Box, Text, Flex, Image, Spacer, useColorModeValue } from '@chakra-ui/react';
import { ColorModeSwitcher } from '../ColorModeSwitcher';

export const MainNavBar = () => {
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');
  const size = 48;

  const title = '';

  return (
    <Box>
      <Box
        w="full"
        h={size + 'px'}
        borderBottom="1px"
        borderColor={color}
        position="fixed"
        top="0vh"
        zIndex="sticky"
        bg={bg}
      >
        <Box position="relative" top="15%" h="70%" w="full">
          <Flex>
            <Image
              src="/logo_dippin.png"
              alt="logo"
              objectFit="contain"
              position="absolute"
              left="4vw"
              h={size * 0.7 + 'px'}
            />
            <Spacer />
            <Text fontSize={size * 0.5 + 'px'}>{title}</Text>
            <Spacer />
            <ColorModeSwitcher position="relative" bottom="4px" right="9vw" />
            <Box
              className="fa-light fa-bell"
              fontSize={size * 0.5 + 'px'}
              position="relative"
              top={size * 0.1 + 'px'}
              right="8vw"
            />
            <Box
              className="fa-light fa-comment"
              fontSize={size * 0.5 + 'px'}
              position="relative"
              top={size * 0.1 + 'px'}
              right="4vw"
            />
          </Flex>
        </Box>
      </Box>
    </Box>
  );
};
