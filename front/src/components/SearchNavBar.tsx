import {
  Box,
  Text,
  Flex,
  Image,
  Spacer,
  useColorModeValue,
  InputGroup,
  InputLeftElement,
  Input,
} from '@chakra-ui/react';

export const SearchNavBar = () => {
  const display = 'none';
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
        h="8vh"
        w="full"
        position="fixed"
        top="16vh"
        borderBottom="1px"
        borderColor={color}
        bg={bg}
      >
        <Box h="6vh" w="full" position="relative" top="1vh" bg="">
          <Flex>
            {/* <Box w="4vh" display={display}> */}
            <Box marginRight="2vh">
              <Box
                className="fa-light fa-angle-left"
                fontSize="3.2vh"
                position="relative"
                top="1.6vh"
                left="2vh"
              />
            </Box>
            <Box flex="1" marginX="1vh">
              <InputGroup padding="0">
                <InputLeftElement
                  h="6vh"
                  pointerEvents="none"
                  children={<Box fontSize="3vh" className="fa-regular fa-magnifying-glass" />}
                />
                <Input
                  h="6vh"
                  variant="filled"
                  borderRadius="1.5vh"
                  type="tel"
                  placeholder="검색어를 입력하세요."
                />
              </InputGroup>
            </Box>
            <Box marginLeft="2vh">
              <Box position="relative" h="6vh" right="2vh">
                {rightButton}
              </Box>
            </Box>
          </Flex>
        </Box>
      </Box>
    </Box>
  );
};
