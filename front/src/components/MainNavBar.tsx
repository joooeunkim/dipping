import { Box, Text, Flex, Image, Spacer, useColorModeValue } from '@chakra-ui/react';

export const MainNavBar = () => {
  const title = 'Titles';
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');
  return (
    <Box>
      <Box
        h="8vh"
        w="full"
        position="fixed"
        top="0vh"
        borderBottom="1px"
        borderColor={color}
        bg={bg}
      >
        <Box h="6vh" w="full" position="relative" top="1vh" bg="">
          <Flex>
            <Image
              src="/logo_icon.png"
              objectFit="contain"
              position="absolute"
              left="2vh"
              alt="logo"
              h="6vh"
            />
            <Spacer />
            <Text fontSize="3.2vh" position="relative" top="0.6vh">
              {title}
            </Text>
            <Spacer />
            <Box
              className="fa-light fa-bell"
              fontSize="3.2vh"
              position="absolute"
              top="1.6vh"
              right="6.2vh"
            />
            <Box
              className="fa-light fa-comment"
              fontSize="3.2vh"
              top="1.6vh"
              position="absolute"
              right="2vh"
            />
          </Flex>
        </Box>
      </Box>
    </Box>
  );
};
