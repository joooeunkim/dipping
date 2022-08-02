import { Box, Text, Flex, Image, Spacer, useColorModeValue } from '@chakra-ui/react';

export const ModalNavBar = () => {
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');

  const title = '댓글';

  const rightButton = (
    <Text
      fontSize="2.6vh"
      position="relative"
      top="0.8vh"
      fontWeight="bold"
      margin="0"
      color="cyan.400"
    >
      완료
    </Text>
  );

  return (
    <Box>
      <Box
        h="8vh"
        w="full"
        position="fixed"
        top="8vh"
        borderBottom="1px"
        borderColor={color}
        bg={bg}
      >
        <Box h="6vh" w="full" position="relative" top="1vh" bg="">
          <Flex>
            <Box
              className="fa-light fa-angle-left"
              fontSize="3.2vh"
              position="absolute"
              top="1.6vh"
              left="2vh"
            />
            <Spacer />
            <Text fontSize="2.6vh" position="relative" top="0.8vh">
              {title}
            </Text>
            <Spacer />
            <Box position="absolute" h="6vh" right="2vh">
              {rightButton}
            </Box>
          </Flex>
        </Box>
      </Box>
    </Box>
  );
};
