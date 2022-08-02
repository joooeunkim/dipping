import {
  Box,
  Text,
  Flex,
  useColorModeValue,
  InputGroup,
  InputLeftElement,
  Input,
} from '@chakra-ui/react';

export const SearchNavBar = () => {
  const display = '';
  const bg = useColorModeValue('white', 'gray.800');
  const color = useColorModeValue('gray.200', 'gray.600');

  const title = '댓글';

  const rightButton = (
    <Text fontSize="2.4vh" position="relative" top="1vh" fontWeight="bold" margin="0">
      취소
    </Text>
  );

  return (
    <Box>
      <Box
        h="48px"
        w="full"
        position="fixed"
        top="96px"
        borderBottom="1px"
        borderColor={color}
        bg={bg}
      >
        <Box position="relative" top="6px" h="36px" w="full" bg="">
          <Flex h="36px">
            <Box
              display={display}
              marginRight="2vw"
              className="fa-light fa-angle-left"
              fontSize="32px"
              position="relative"
              top="2px"
              left="4vw"
              bg=""
            />
            <Box flex="1" position="relative" top="2px" marginX="4vw">
              <InputGroup>
                <InputLeftElement
                  h="32px"
                  children={<Box fontSize="16px" className="fa-regular fa-magnifying-glass" />}
                />
                <Input
                  h="32px"
                  variant="filled"
                  borderRadius="1.5vh"
                  type="text"
                  placeholder="검색어를 입력하세요."
                />
              </InputGroup>
            </Box>
            <Box
              display={display}
              position="relative"
              right="4vw"
              h="36px"
              w="48px"
              lineHeight="36px"
              textAlign="right"
              fontSize="16px"
              fontWeight="bold"
              color="cyan.400"
              bg=""
            >
              취소
            </Box>
          </Flex>
        </Box>
      </Box>
    </Box>
  );
};
