import { Text, Box, Button, Center, ChakraProvider, Container, Heading } from '@chakra-ui/react';

// 회원가입 방법 선택 컴포넌트
export const Register = () => {
  return (
    <ChakraProvider>
      <Container maxW="480px" w="95%" bg="" h="100vh" margin="0 auto">
        <Center mt="32px">
          <Heading as="h1" h="110px" bg="gray.200">
            LOGO
          </Heading>
        </Center>
        <Center mt="128px" w="100%" flexWrap="wrap">
          <Box w="100%">
            <Button
              w="100%"
              bg="cyan.400"
              color="white"
              _hover={{
                bg: 'cyan.500',
              }}
              _active={{
                bg: 'cyan.500',
              }}
            >
              디핑 회원가입
            </Button>
          </Box>
          <Box w="100%" mt="8px">
            <Button
              w="100%"
              bg="white"
              color="black"
              _hover={{
                bg: 'gray.100',
              }}
              _active={{
                bg: 'gray.100',
              }}
            >
              구글 회원가입
            </Button>
          </Box>
          <Box w="100%" mt="8px">
            <Button
              w="100%"
              bg="#FEE500"
              color="#392020"
              _hover={{
                bg: '#EFD800',
              }}
              _active={{
                bg: '#EFD800',
              }}
            >
              카카오 회원가입
            </Button>
          </Box>
          <Text mt="64px" color="cyan.400">
            로그인 화면으로 돌아가기
          </Text>
        </Center>
      </Container>
    </ChakraProvider>
  );
};
