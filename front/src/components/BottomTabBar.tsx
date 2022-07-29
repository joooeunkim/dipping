import * as React from 'react';
import { Box, VStack, Spacer, Button, Flex, Circle } from '@chakra-ui/react';

export const BottomTabBar = () => {
  const icons_light = [
    'fa-light fa-house',
    'fa-light fa-music',
    'fa-regular fa-plus',
    'fa-light fa-compact-disc',
    'fa-light fa-music',
  ];

  return (
    <Box
      w="full"
      h="8%"
      borderTopWidth="1px"
      borderColor="gray.200"
      position="fixed"
      bottom="0px"
      zIndex="popover"
      bg="white"
    >
      <Box h="40%" position="absolute" top="30%" w="full">
        <Flex>
          <Spacer />
          <Box fontSize="3vh" className={icons_light[0]} color="cyan.400" />
          <Spacer />
          <Box fontSize="3vh" className={icons_light[1]} />
          <Spacer />
          <Circle
            size="5vh"
            position="relative"
            bottom="1vh"
            bgGradient="linear(to-br, blue.400, cyan.200)"
          >
            <Box fontSize="3vh" className={icons_light[2]} color="white" />
          </Circle>
          <Spacer />
          <Box fontSize="3vh" className={icons_light[3]} />
          <Spacer />
          <Box fontSize="3vh" className={icons_light[4]} />
          <Spacer />
        </Flex>
      </Box>
    </Box>
  );
};
