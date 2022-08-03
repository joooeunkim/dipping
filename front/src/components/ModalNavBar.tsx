import { Box, Text, Flex, Image, Spacer, useColorModeValue } from '@chakra-ui/react';

export const ModalNavBar = (props: any) => {
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');

  const { title, leftElement, rightElement } = props;
  // const title = '댓글';
  // const rightElement = '완료';
  // const rightElement = <Box className="fa-light fa-music" fontSize="24px" bg="" />;

  return (
    <Box>
      <Box
        h="48px"
        w="full"
        position="fixed"
        top="0px"
        borderBottom="1px"
        borderColor={color}
        bg={bg}
      >
        <Box id="bar" position="relative" top="6px" h="36px" w="full" bg="">
          <Box
            position="absolute"
            left="4vw"
            float="left"
            textAlign="left"
            fontSize="16px"
            fontWeight="bold"
            lineHeight="36px"
            bg=""
          >
            {leftElement}
          </Box>
          <Box
            position="absolute"
            right="4vw"
            float="right"
            textAlign="right"
            fontSize="16px"
            fontWeight="bold"
            lineHeight="36px"
            bg=""
          >
            {rightElement}
          </Box>
          <Box w="128px" margin="auto" textAlign="center" fontSize="16px" lineHeight="36px" bg="">
            {title}
          </Box>
        </Box>
      </Box>
    </Box>
  );
};
