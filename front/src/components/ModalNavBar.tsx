import { Box, Text, Flex, Image, Spacer, useColorModeValue } from '@chakra-ui/react';

export const ModalNavBar = () => {
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');

  const title = '댓글';
  const rightButton = '완료';
  // const rightButton = <Box className="fa-light fa-music" fontSize="24px" bg="" />;

  return (
    <Box>
      <Box
        h="48px"
        w="full"
        position="fixed"
        top="48px"
        borderBottom="1px"
        borderColor={color}
        bg={bg}
      >
        <Box id="bar" position="relative" top="6px" h="36px" w="full" bg="">
          <Box
            className="fa-light fa-angle-left"
            fontSize="32px"
            position="absolute"
            top="2px"
            left="4vw"
            bg=""
          />
          <Box
            position="absolute"
            right="4vw"
            h="36px"
            w="48px"
            float="right"
            lineHeight="36px"
            textAlign="right"
            fontSize="16px"
            fontWeight="bold"
            color="cyan.400"
            bg=""
          >
            {rightButton}
          </Box>
          <Box w="128px" margin="auto" textAlign="center" fontSize="16px" lineHeight="36px" bg="">
            {title}
          </Box>
        </Box>
      </Box>
    </Box>
  );
};
