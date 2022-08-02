import * as React from 'react';
import { Box, VStack, Spacer, Button, Flex, Circle, useColorModeValue } from '@chakra-ui/react';

export const BottomTabBar = () => {
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');
  const size = 48;

  const icons_light = [
    'fa-light fa-house',
    'fa-light fa-music',
    'fa-regular fa-plus',
    'fa-light fa-compact-disc',
    'fa-light fa-user',
  ];

  return (
    <Box
      w="full"
      h={size + 'px'}
      borderTopWidth="1px"
      borderColor={color}
      position="fixed"
      bottom="0px"
      zIndex="popover"
      bg={bg}
    >
      <Box position="relative" top="25%" h="50%" w="full">
        <Flex>
          <Box w="8vw" />
          <Box fontSize={size * 0.5 + 'px'} className={icons_light[0]} color="cyan.400" />
          <Spacer />
          <Box fontSize={size * 0.5 + 'px'} className={icons_light[1]} />
          <Spacer />
          <Circle
            size={size * 0.7 + 'px'}
            position="relative"
            bottom={size * 0.1 + 'px'}
            bgGradient="linear(to-br, blue.400, cyan.200)"
          >
            <Box fontSize={size * 0.5 + 'px'} className={icons_light[2]} color={bg} />
          </Circle>
          <Spacer />
          <Box fontSize={size * 0.5 + 'px'} className={icons_light[3]} />
          <Spacer />
          <Box fontSize={size * 0.5 + 'px'} className={icons_light[4]} />
          <Box w="8vw" />
        </Flex>
      </Box>
    </Box>
  );
};
