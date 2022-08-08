import * as React from 'react';
import { Box, VStack, Spacer, Button, Flex, Circle, useColorModeValue } from '@chakra-ui/react';
import { Link, useLocation } from 'react-router-dom';

export const BottomTabBar = () => {
  const bgColor = useColorModeValue('white', 'gray.800');
  const borderColor = useColorModeValue('gray.200', 'gray.600');
  const size = 48;

  const icons_light = [
    'fa-light fa-house',
    'fa-light fa-music',
    'fa-regular fa-plus',
    'fa-light fa-compact-disc',
    'fa-light fa-user',
  ];
  const pathname = useLocation().pathname;

  const iconColors = [
    pathname === '/' ? 'cyan.200' : '',
    pathname === '/search' ? 'cyan.200' : '',
    pathname === '/dippin' ? 'cyan.200' : '',
    pathname === '/profile' ? 'cyan.200' : '',
  ];

  console.log('rendered' + pathname);

  // useEffect(() => {
  //   console.log(location);
  // }, [location]);

  return (
    <Box
      w="full"
      h={size + 'px'}
      borderTopWidth="1px"
      borderColor={borderColor}
      position="fixed"
      bottom="0px"
      zIndex="sticky"
      bg={bgColor}
    >
      <Box position="relative" top="25%" h="50%" w="full">
        <Flex>
          <Box w="8vw" />
          <Link to="/">
            <Box fontSize={size * 0.5 + 'px'} className={icons_light[0]} color={iconColors[0]} />
          </Link>
          <Spacer />

          <Link to="/search">
            <Box fontSize={size * 0.5 + 'px'} className={icons_light[1]} color={iconColors[1]} />
          </Link>
          <Spacer />

          <Circle
            size={size * 0.7 + 'px'}
            position="relative"
            bottom={size * 0.1 + 'px'}
            bgGradient="linear(to-br, blue.400, cyan.200)"
          >
            <Box fontSize={size * 0.5 + 'px'} className={icons_light[2]} color={bgColor} />
          </Circle>
          <Spacer />

          <Link to="/dippin">
            <Box fontSize={size * 0.5 + 'px'} className={icons_light[3]} color={iconColors[2]} />
          </Link>
          <Spacer />

          <Link to="/profile">
            <Box fontSize={size * 0.5 + 'px'} className={icons_light[4]} color={iconColors[3]} />
          </Link>

          <Box w="8vw" />
        </Flex>
      </Box>
    </Box>
  );
};
